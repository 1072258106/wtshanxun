package cn.mandroid.wtshanxun.Model;

/**
 * Created by wangtao on 2016-06-27.
 */
public interface ApiCallback {
    void onSuccess(String result);
    void error();
}
