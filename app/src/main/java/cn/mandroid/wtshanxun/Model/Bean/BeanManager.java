package cn.mandroid.wtshanxun.Model.Bean;

import android.content.Context;



import cn.mandroid.wtshanxun.utils.CheckUtils;
import cn.mandroid.wtshanxun.utils.Preference;

/**
 * Created by Administrator on 2015-11-15.
 */
public class BeanManager {
    private static UserBean userBean;

    public static UserBean getUserBean(Context context) {
        Preference preference = Preference.instance(context);
        if (CheckUtils.stringIsEmpty(preference.getString(Preference.SX_USER))) {
            return null;
        }
        if (userBean == null) {
            userBean = new UserBean();
            String user = preference.getString(Preference.SX_USER);
            userBean.setSessionId(preference.getString(Preference.SESSION_ID));
            userBean.setSxAcount(user);
        }
        return userBean;
    }

    public static void cleanUser(Context context) {
        userBean = null;
    }
}
