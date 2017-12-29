package personal.zdatbit;

import org.junit.Test;
import personal.zdatibt.UserService;

public class HelloTest {
    @Test
    public void test(){
        UserService u = new UserService();
        u.sayHello();
    }
}
