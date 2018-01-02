package zdatbit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zdatbit.user.IService.IUserService;
import zdatbit.user.dao.UserDao;
import zdatbit.user.domain.User;

import java.util.List;

/**
 * @author zhangdi
 * @date 创建时间: 2017/12/29 17:41
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userMapper;

    public void sayHello(){
        System.out.println("hello user");
    }
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
}
