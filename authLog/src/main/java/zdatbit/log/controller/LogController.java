package zdatbit.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import zdatbit.log.domain.AuthLog;
import zdatbit.log.service.LogService;

import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService service;

    @RequestMapping("/index")
    public ModelAndView listAll(){
        ModelAndView mv = new ModelAndView();
        List<AuthLog> log = service.list(AuthLog.class);
        mv.addObject("logs",log);
        return mv;
    }
}
