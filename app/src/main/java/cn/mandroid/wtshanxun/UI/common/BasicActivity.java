package cn.mandroid.wtshanxun.UI.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import cn.mandroid.wtshanxun.event.ExitApp;
import cn.mandroid.wtshanxun.utils.Preference;
import cn.mandroid.wtshanxun.utils.PreferenceHelper;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015-11-22.
 */
public class BasicActivity extends Activity {
    protected Context context;
    protected Preference preference;
    protected PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        preference = Preference.instance(context);
        preferenceHelper = PreferenceHelper.instance(context);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    public void onEvent(ExitApp exit) {
        finish();
    }

    protected void exitApp() {
        EventBus.getDefault().post(new ExitApp());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
