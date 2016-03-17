package cn.mandroid.wtshanxun.UI.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015-10-21.
 */
public class SuperDialog extends Dialog {
    public SuperDialog(Context context, int theme) {
        super(context, theme);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }
}
