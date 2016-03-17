package cn.mandroid.wtshanxun.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import cn.mandroid.wtshanxun.Model.Bean.BeanManager;
import cn.mandroid.wtshanxun.utils.Preference;

/**
 * Created by Administrator on 2015-11-15.
 */
public class PreferenceHelper {
    Context context;
    Preference preference;
    private static PreferenceHelper helper;

    private PreferenceHelper(Context context) {
        this.context = context;
        preference = Preference.instance(context);
    }

    public static PreferenceHelper instance(Context context) {
        if (helper == null) {
            helper = new PreferenceHelper(context);
        }
        return helper;
    }

    public void saveUser(String json) {
        JSONObject jsonObject = null;
        BeanManager.cleanUser(context);
        try {
            jsonObject = new JSONObject(json);
            preference.putString(Preference.SX_USER, jsonObject.getString("sxAcount"));
            preference.putString(Preference.SESSION_ID, jsonObject.has("sessionId") ? jsonObject.getString("sessionId") : "");
            preference.putBoolean(Preference.AUTO_GET_PASSWORD, false);
        } catch (JSONException e) {
        }

    }
}
