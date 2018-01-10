package zdatbit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zdatbit.user.service.base.TemplateService;
import zdatbit.user.dao.TemplateDao;
import zdatbit.user.domain.User;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangdi
 * @date 创建时间: 2017/12/29 17:41
 */
@Service
public class UserService extends TemplateService {

    @Autowired
    private TemplateDao dao;

    public void sayHello() {
        System.out.print("hello world");
    }

    @PostConstruct
    public boolean initUsers(){
        List<User> list = new ArrayList<User>();

        User user0 = new User();
        user0.setUserName("zdatbit");
        user0.setUserPassword("123456");
        list.add(user0);

        User user1 = new User();
        user1.setUserName("zda");
        user1.setUserPassword("123456");
        list.add(user1);
        try {
            return dao.saveBatch(list);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<User> selectAll() {
        return dao.list(User.class);
    }
}
