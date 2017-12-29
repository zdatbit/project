package zdatbit.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    @RequestMapping("/admin")
    public ModelAndView admin(){
        return new ModelAndView("admin/index");
    }
    @RequestMapping("/list")
    public ModelAndView userList(){
        return new ModelAndView("admin/table-font-list");
    }
}
