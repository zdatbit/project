package zdatbit.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import zdatbit.log.service.ILogService;
import zdatbit.log.service.LogService;
import zdatbit.user.domain.User;
import zdatbit.user.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangdi
 * @date 创建时间: 2018/1/9 16:31
 */
@Controller
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private ILogService logService;

    private Integer retCode;
    private String retMsg;
    private Map<String,Object> resultMap = new HashMap<>();

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
    @RequestMapping("/userAdd")
    public ModelAndView userAdd(){
        ModelAndView mv = new ModelAndView("admin/userAdd");
        return mv;
    }
    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(User user){
//        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("spring-dubbo.xml");
//        cxt.start();
//        ILogService logService = cxt.getBean(LogService.class);
        logService.logSave(null);
        service.save(user);
        retCode=1;
        retMsg="执行成功";
        resultMap.put("retCode",retCode);
        resultMap.put("retMsg",retMsg);
        return resultMap;
    }
}
