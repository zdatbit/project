package zdatbit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zdatbit.user.service.base.TemplateService;
import zdatbit.user.dao.TemplateDao;
import zdatbit.user.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    public List<User> selectAll() {
        return dao.list(User.class);
    }
}
