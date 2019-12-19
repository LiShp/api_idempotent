package scm.ims.idempotent.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 生成token,放入redis中
 * Created by yz on 2018/7/29.
 */
@Component
public class RedisTokenUtils {

	@Autowired
	private BaseRedisUtils baseRedisService;

	private static final long TOKENTIME = 120;

	/**
	 * 调用baseRedisService的setToken方法存放数据
	 * @param method
	 * @param token
	 * @return
	 */
	public Boolean setToken(String method, String token){ return baseRedisService.setKey(method,token); }
	/**
	 * 调用baseRedisService的etTimeOutKey方法设置过期时间
	 * @param method
	 */
	public void setTimeOutKey(String method) {
		baseRedisService.setTimeOutKey(method,TOKENTIME);
	}
	/**
	 * 调用baseRedisService的delkey方法删除key
	 * @param method
	 */
	public void delKey(String method) { baseRedisService.delKey(method); }


}
