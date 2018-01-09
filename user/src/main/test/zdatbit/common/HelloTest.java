package zdatbit.common;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zdatbit.user.service.UserService;

public class HelloTest {
    @Autowired
    private UserService service;
    @Test
    public void test(){
        UserService u = new UserService();
        u.sayHello();
    }
}
