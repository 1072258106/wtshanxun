package cn.mandroid.wtshanxun.UI.common;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import cn.mandroid.wtshanxun.R;
import cn.mandroid.wtshanxun.UI.dialog.ExitDialog;
import cn.mandroid.wtshanxun.UI.dialog.RouterSetDialog;
import cn.mandroid.wtshanxun.UI.dialog.UpdateDialog;
import cn.mandroid.wtshanxun.utils.MToast;

/**
 * Created by Administrator on 2015-10-05.
 */
public class PopupWindowClick implements View.OnClickListener {
    private Context context;
    private PopupWindow popupWindow;

    public PopupWindowClick(Context context, PopupWindow popupWindow) {
        this.context = context;
        this.popupWindow = popupWindow;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_rl1:
                RouterSetDialog.instance(context).show();
                break;
            case R.id.popup_rl3: {
                PgyUpdateManager.register((BasicActivity) context, new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        MToast.show(context, "暂无更新");
                    }

                    @Override
                    public void onUpdateAvailable(String s) {
                        UpdateDialog.instance(context, getAppBeanFromString(s)).show();
                    }
                });

//                Intent intent = new Intent(context, UpdateActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
                break;
            }
            case R.id.popup_rl4:
                ExitDialog.instance(context).show();
                break;
            default:
                break;
        }
        popupWindow.dismiss();
    }

}
