package scm.ims.idempotent.service;

import scm.ims.idempotent.entity.User;

/**
 * Created by yz on 2018/7/29.
 */
public interface UserService {
    void addUser(User user) throws Exception;

}
