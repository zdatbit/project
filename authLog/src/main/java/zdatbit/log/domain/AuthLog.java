package zdatbit.log.domain;

import java.io.Serializable;
import java.util.Date;

public class AuthLog implements Serializable{
    private Integer logID;
    private String ip;
    private Date createTime;

    public Integer getLogID() {
        return logID;
    }

    public void setLogID(Integer logID) {
        this.logID = logID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
