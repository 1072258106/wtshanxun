package cn.mandroid.wtshanxun.Model.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/28.
 */
public class IpConfig implements Serializable{
    private String ip;
    private long timestamp;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
