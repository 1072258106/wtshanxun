package cn.mandroid.wtshanxun.Model;

import cn.mandroid.wtshanxun.Model.Bean.FetchResult;

/**
 * Created by Administrator on 2015-11-22.
 */
public interface FetchCallback {
    public void get(FetchResult result);
    public void error();
}
