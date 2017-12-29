package zdatbit.common;

import zdatbit.user.domain.User;

import java.util.List;

public interface IUserService {
    public void sayHello();
    public List<User> selectAll();

}
