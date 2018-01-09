package zdatbit.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import zdatbit.user.domain.User;
import zdatbit.user.service.UserService;

import java.util.List;

/**
 * @author zhangdi
 * @date 创建时间: 2018/1/9 16:31
 */
@Controller
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("admin/index");
    }

    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView("admin/table-font-list");
        List<User> userList = service.selectAll();
        mv.addObject("userList",userList);
        return mv;
    }
}
