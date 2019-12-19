package scm.ims.idempotent.service.impl;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import scm.ims.idempotent.annotation.RecordAnnotation;
import scm.ims.idempotent.controller.UserPageController;
import scm.ims.idempotent.entity.User;
import scm.ims.idempotent.mapper.UserMapper;
import scm.ims.idempotent.service.UserService;

import java.time.LocalTime;

/**
 * Created by yz on 2018/7/29.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    private final int totalNum = 100000;
//    @CreatToken
    @Override
   @RecordAnnotation(key="#user",desc = "幂等")
    public void addUser(User user) {
    	System.out.println("+++_+_+_+_+_+user"+user);
         userMapper.addUser(user);
    }



}
