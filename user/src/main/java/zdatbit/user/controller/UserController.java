package zdatbit.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangdi
 * @date 创建时间: 2017/12/29 14:22
 */

@Controller
public class UserController {
    @RequestMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().write("hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
