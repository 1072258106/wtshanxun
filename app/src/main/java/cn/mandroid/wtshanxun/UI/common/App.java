package cn.mandroid.wtshanxun.UI.common;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;

import org.androidannotations.annotations.EApplication;


/**
 * Created by Administrator on 2015-11-22.
 */
@EApplication
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
    }
}
