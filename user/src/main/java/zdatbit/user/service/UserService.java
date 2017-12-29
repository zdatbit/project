package zdatbit.user.service;

import zdatbit.common.IUserService;
import zdatbit.user.domain.User;

import java.util.List;

/**
 * @author zhangdi
 * @date 创建时间: 2017/12/29 17:41
 */
public class UserService implements IUserService{

    public void sayHello(){
        System.out.println("hello user");
    }
    public List<User> selectAll() {
        return null;
    }
}
