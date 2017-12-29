package zdatbit.user;


import zdatbit.common.IUserService;
import zdatbit.user.domain.User;

import java.util.List;

public class UserService implements IUserService {

    public void sayHello() {
        System.out.println("hello world");
    }

    public List<User> selectAll(){
        return null;
    }
}
