package zdatbit.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import zdatbit.user.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("admin/login");
    }
    /**
     * 后台首页
     * @return
     */
    @RequestMapping("/admin")
    public ModelAndView admin(){
        return new ModelAndView("admin/index");
    }

    /**
     * 用户列表
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView userList(){
        ModelAndView model = new ModelAndView("admin/table-font-list");
//        List<User> user =
//        model.addObject("user",user);
        return model;
    }
}
