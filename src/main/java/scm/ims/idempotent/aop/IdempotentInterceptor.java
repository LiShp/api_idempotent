package scm.ims.idempotent.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import scm.ims.idempotent.annotation.RecordAnnotation;
import scm.ims.idempotent.utils.ConstantUtils;
import scm.ims.idempotent.utils.RedisTokenUtils;

import java.util.UUID;

/**
 * 接口幂等切面
 * Created by yz on
 */
@Aspect
@Component
public class IdempotentInterceptor {

    @Autowired
    private RedisTokenUtils redisToken;

    private static final Logger log = LoggerFactory.getLogger(IdempotentInterceptor.class);
    //spel解析器
    ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(recordAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, RecordAnnotation recordAnnotation) throws Throwable {
        //获取当前切面方法的形参
        //new LocalVariableTableParameterNameDiscoverer().getParameterNames(method) spring提供的获取方法中形参的函数
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer()
                .getParameterNames(((MethodSignature) joinPoint.getSignature()).getMethod());
        //获取方法名
        Object target = joinPoint.getTarget().getClass().getName();
        //获取参数值
        Object[] args = joinPoint.getArgs();
        //调用spel表达式进行解析
        Object object = getRequest(recordAnnotation.key(), parameterNames, args);
        String  idempotent= ConstantUtils.IDEMPOTENT;

        //生成业务主键作为redis的key
        String method =idempotent+"-"+ target.toString()+"-"+object.toString();

        log.info("method++++++++++:"+method);

            //通过uuid生成value值
            String token = "token"+UUID.randomUUID();
            //调用redisToken方法存放redis的key-value
            Boolean doesitexist = redisToken.setToken(method,token);
            //校验
            if(doesitexist==false) {
            	throw new Exception("业务正在执行中!");
            }
        try {
        	 // 放行
            Object proceed = joinPoint.proceed();
            //业务执行成功后设置过期时间
            redisToken.setTimeOutKey(method);
            return proceed;
		} catch (Exception e) {
            //发生异常删除相关的key
            redisToken.delKey(method);
            throw new Exception("业务出现异常！！！！");
		}

    }

    /**
     * 通过spring Spel 获取参数
     * @param key               定义的key值 以#开头 例如:#user
     * @param parameterNames    形参
     * @param values            形参值
     * @return
     */
    public Object getRequest(String key, String[] parameterNames, Object[] values) {
        //spel上下文陪衬
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], values[i]);
        }
        return parser.parseExpression(key).getValue(context);
    }
}
