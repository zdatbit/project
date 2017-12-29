package zdatbit.common;

import org.junit.Test;
import zdatbit.user.UserService;

public class HelloTest {
    @Test
    public void test(){
        UserService u = new UserService();
        u.sayHello();
    }
}
