package cn.mandroid.wtshanxun.Model.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015-11-22.
 */
public class BasicBean implements Serializable{
    private int status;
    private String msg;

    public String getData() {
        return data.toString();
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Object data;
}
