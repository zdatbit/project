package zdatbit.user.IService;

import zdatbit.user.domain.User;

import java.util.List;

/**
 * @author zhangdi
 * @date 创建时间: 2018/1/2 9:37
 */
public interface IUserService {
    public void sayHello();
    public List<User> selectAll();
}
