package cn.mandroid.wtshanxun.UI.common;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import cn.mandroid.wtshanxun.R;
import cn.mandroid.wtshanxun.UI.dialog.ExitDialog;
import cn.mandroid.wtshanxun.UI.dialog.RouterSetDialog;

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
