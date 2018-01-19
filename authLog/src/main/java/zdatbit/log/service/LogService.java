package zdatbit.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zdatbit.log.dao.TemplateDao;
import zdatbit.log.domain.AuthLog;
import zdatbit.log.service.base.TemplateService;

import java.util.Date;


@Service
public class LogService extends TemplateService implements ILogService{

    @Autowired
    private TemplateDao dao;

    public boolean logSave(AuthLog log){
        if(log==null){
            log = new AuthLog();
            log.setCreateTime(new Date());
            log.setIp("127.0.0.1");
        }
        return dao.save(log);
    }
}
