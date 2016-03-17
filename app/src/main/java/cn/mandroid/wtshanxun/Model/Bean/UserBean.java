package cn.mandroid.wtshanxun.Model.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015-11-15.
 */
public class UserBean extends BasicBean implements Serializable{
    private String sxAcount;
    private String sessionId;
    private int uid;
    private int coin ;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private String password;
    public String getSxAcount() {
        return sxAcount;
    }

    public void setSxAcount(String sxAcount) {
        this.sxAcount = sxAcount;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public void setCoin(int coin) {
        this.coin = coin;
    }

}
