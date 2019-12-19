package scm.ims.idempotent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import scm.ims.idempotent.annotation.RecordAnnotation;
import scm.ims.idempotent.entity.User;
import scm.ims.idempotent.service.UserService;

/**
 * 处理表单提交请求
 * 
 */
@RestController
public class UserPageController {
    private static final Logger log = LoggerFactory.getLogger(UserPageController.class);
    @Autowired
    private UserService userService;

    // 使用CheckToken注解方式保证请求幂等性
    @RequestMapping(value = "/addUserForPage")


    @RecordAnnotation(key="#user")
    public String addOrdffys(User user) throws Exception{
        // 业务逻辑
        userService.addUser(user);
        return "success";
    }

    @RequestMapping("OrderTest")
    public String order(){


        return "";
    }


}
