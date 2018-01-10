package zdatbit.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zdatbit.log.dao.TemplateDao;
import zdatbit.log.domain.AuthLog;
import zdatbit.log.service.base.TemplateService;


@Service
public class LogService extends TemplateService {

    @Autowired
    private TemplateDao dao;

    public boolean logSave(AuthLog log){
        return dao.save(log);
    }
}
