package scm.ims.idempotent.mapper;

import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;
import scm.ims.idempotent.entity.User;

/**
 * Created by yz on 2018/7/29.
 */
public interface UserMapper {
    void addUser(User user);
    User getUser(int id);
    void removeUser(int id);


}
