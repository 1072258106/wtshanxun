package cn.mandroid.wtshanxun.Model.Bean;

/**
 * Created by Administrator on 2016/6/6.
 */
public class HeartBean extends BasicBean {
    private String address;
    private int port;
    private String data;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
