package zdatbit.log.service;

import zdatbit.log.domain.AuthLog;

/**
 * @author zhangdi
 * @date 创建时间: 2018/1/19 15:22
 */
public interface ILogService {
    public boolean logSave(AuthLog log);
}
