package scm.ims.idempotent.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 集成封装redis
 * Created
 */
@Component
public class BaseRedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *
     * 接受参数存放到redis中
     * @param method
     * @param data
     * @return
     */
    public boolean setKey(String method,String data){
        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(method,data);
        //校验返回结果
        return setIfAbsent;
    }
    /**
     *
     * 根据key获取value值
     * @param method
     * @return
     */
    public String getString(String method){
        return stringRedisTemplate.opsForValue().get(method);
    }
    /**
     *
     * 业务执行成功后给key设置过期时间
     * @param method
     * @param tokentime
     */
	public void setTimeOutKey(String method, long tokentime) {
		// TODO Auto-generated method stub
    	stringRedisTemplate.expire(method,tokentime,TimeUnit.SECONDS);
	}
    /**
     * 发生异常是删除key
     * @param method
     */
    public void delKey(String method) {
        stringRedisTemplate.delete(method);
    }
}
