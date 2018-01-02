package zdatbit.user.dao;

import org.springframework.stereotype.Repository;
import zdatbit.user.domain.User;

import java.util.List;

/**
 * @author zhangdi
 * @date 创建时间: 2018/1/2 9:53
 */
public interface UserDao {
    public List<User> selectAll();
    public int insert(User user);
}
