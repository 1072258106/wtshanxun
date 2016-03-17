package cn.mandroid.wtshanxun.event;

import cn.mandroid.wtshanxun.Model.Bean.UserBean;

/**
 * Created by Administrator on 2015-11-22.
 */
public class GetUserEvent {
    public UserBean getUserBean() {
        return userBean;
    }

    private UserBean userBean;
    public GetUserEvent(UserBean userBean) {
        this.userBean=userBean;
    }
}
