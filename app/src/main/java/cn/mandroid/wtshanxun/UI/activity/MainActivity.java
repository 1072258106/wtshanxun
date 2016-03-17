package cn.mandroid.wtshanxun.UI.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.UnsupportedEncodingException;

import cn.mandroid.wtshanxun.Model.Bean.BeanManager;
import cn.mandroid.wtshanxun.Model.Bean.UserBean;
import cn.mandroid.wtshanxun.Model.FetchCallback;
import cn.mandroid.wtshanxun.Model.ShanxunManager;
import cn.mandroid.wtshanxun.Model.UserManager;
import cn.mandroid.wtshanxun.R;
import cn.mandroid.wtshanxun.UI.Service.HeartService_;
import cn.mandroid.wtshanxun.UI.common.BasicActivity;
import cn.mandroid.wtshanxun.UI.common.PopupWindowClick;
import cn.mandroid.wtshanxun.UI.dialog.NoticeDialog;
import cn.mandroid.wtshanxun.UI.dialog.RouterSetDialog;
import cn.mandroid.wtshanxun.UI.widget.ActionBar;
import cn.mandroid.wtshanxun.UI.widget.AlwaysMarqueeTextView;
import cn.mandroid.wtshanxun.event.CheckCallBackEvent;
import cn.mandroid.wtshanxun.event.StopHeartEvent;
import cn.mandroid.wtshanxun.utils.CheckUtils;
import cn.mandroid.wtshanxun.utils.MToast;
import cn.mandroid.wtshanxun.utils.Preference;
import cn.mandroid.wtshanxun.utils.Tools;

@EActivity(R.layout.activity_main)
public class MainActivity extends BasicActivity implements ActionBar.OnHeadImgClickListenner {
    @ViewById
    ActionBar actionBar;
    @ViewById
    TextView sxAcountText;
    @ViewById
    EditText sxAcountEdit;
    @ViewById
    TextView exchangeBut;
    @ViewById
    EditText sxPasswordEdit;
    @ViewById
    Button mainSubmit;
    @ViewById
    CheckBox autoGetPasswordCheckBox;
    @ViewById
    CheckBox sendHeartCheckBox;
    @ViewById
    AlwaysMarqueeTextView bottomText;
    @Bean
    UserManager mUserManager;
    @Bean
    ShanxunManager mShanxunManager;
    PopupWindow popupWindow;
    boolean sxAcountValid;
    PowerManager pm;
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setWifiNeverDormancy(this);
        setWake();
        Tools.initSign(this);
    }

    private void setWake() {
        pm = (PowerManager) getApplication().getSystemService(Context.POWER_SERVICE);
        // 保持cpu一直运行，不管屏幕是否黑屏
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "CPUKeepRunning");
        wakeLock.acquire();
    }

    @AfterViews
    void afterView() {
        actionBar.setTitle(getString(R.string.app_name));
        actionBar.setLeftImgVisible(View.GONE);
        actionBar.setOnHeadImgClickListenner(this);
        initData(BeanManager.getUserBean(context));
        if (sxAcountText.getText().toString().length() == 0) {
            exchangeButClick();
        }
    }

    private void initNotice() {
        mUserManager.getNotice(context, sxAcountText.getText().toString(), new FetchCallback() {
            @Override
            public void get(String result) {
                bottomText.setText(Html.fromHtml(result));
            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    protected void onResume() {
        initNotice();
        super.onResume();
    }

    private void initData(UserBean bean) {
        if (bean == null) {
            return;
        }
        if (CheckUtils.stringIsEmpty(bean.getSxAcount())) {
            sxAcountEdit.setVisibility(View.VISIBLE);
            exchangeBut.setText("验证账号");
        }
        sxAcountText.setText(bean.getSxAcount());
        sxPasswordEdit.setText(preference.getString(Preference.SX_PASS));
        autoGetPasswordCheckBox.setChecked(preference.getBoolean(Preference.AUTO_GET_PASSWORD));
        sendHeartCheckBox.setChecked(preference.getBoolean(Preference.SEND_HEART));
    }

    @AfterTextChange(R.id.sxAcountEdit)
    void setSxEditChange(TextView editText) {
        if (CheckUtils.sxAcountIsValid(editText.getText().toString().toUpperCase())) {
            sxAcountValid = true;
            editText.setBackgroundResource(R.drawable.bg_view_frame_green);
        } else {
            sxAcountValid = false;
            editText.setBackgroundResource(R.drawable.bg_view_frame_red);

        }
    }

    @AfterTextChange(R.id.sxPasswordEdit)
    void setSxPassEditChange(TextView editText) {
        preference.putString(Preference.SX_PASS, editText.getText().toString());
    }

    @Click(R.id.exchangeBut)
    public void exchangeButClick() {
        if (sxAcountEdit.getVisibility() == View.VISIBLE) {
            String sxAcount = sxAcountEdit.getText().toString().toUpperCase();
            if (sxAcountValid) {
                sxAcountEdit.setVisibility(View.GONE);
                preference.putString(preference.SX_USER, sxAcount);
                exchangeBut.setText("切换账号");
                sxAcountText.setText(sxAcount);
                preference.putBoolean(Preference.USER_VERIFIED, false);
                setMainBut(0);
                HeartService_.intent(context).stop();
            } else {
                MToast.show(context, "闪讯账号格式有误");
            }
        } else {
            exchangeBut.setText("保存");
            sxAcountEdit.setText(preference.getString(Preference.SX_USER));
            sxAcountEdit.setVisibility(View.VISIBLE);
        }
    }

    @CheckedChange({R.id.autoGetPasswordCheckBox, R.id.sendHeartCheckBox})
    public void onChecked(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.autoGetPasswordCheckBox:
                if (isChecked) {
                    String c = "该功能会自动发送短信获取密码，请慎重使用！非闪讯电话卡或双卡手机请关闭此功能，否则无法正常使用软件";
                    new NoticeDialog(context, "注意", c).show();
                }
                preference.putBoolean(Preference.AUTO_GET_PASSWORD, isChecked);
                break;
            case R.id.sendHeartCheckBox:
                preference.putBoolean(Preference.SEND_HEART, isChecked);
                break;
        }
    }

    private void getUserInfo() {
        String sxAcount = preference.getString(Preference.SX_USER);
        if (CheckUtils.sxAcountIsValid(sxAcount)) {
            CheckUtils.networkIsValid(context);
        } else {
            MToast.show(context, "闪讯账号格式不正确!");
        }
    }

    public void onEvent(CheckCallBackEvent event) {
        switch (event.getAction()) {
            case CheckCallBackEvent.CHECK_NETWORK_VALID:
                String sxAcount = preference.getString(Preference.SX_USER).toUpperCase();
                if (!event.isSuccess()) {
                    if (!CheckUtils.isWifiConnected(context)) {
                        MToast.show(context, "连接失败,请检查网络");
                    } else {
                        checkSetting(sxAcountEdit.getText().toString().toUpperCase(), sxPasswordEdit.getText().toString());
                    }
                } else {
                    updateUser(sxAcount, true);
                }
                break;
        }
    }

    private void updateUser(String sxAcount, final boolean showToast) {
        if (showToast) {
            MToast.show(context, "正在获取用户信息!");
        }
        mUserManager.getUserInfo(context, sxAcount, new FetchCallback() {
            @Override
            public void get(String result) {
                preferenceHelper.saveUser(result);
                UserBean userBean = BeanManager.getUserBean(context);
                if (showToast) {
                    MToast.show(context, "获取成功!");
                }
                mShanxunManager.checkNeedSentHeart(context);
                initData(userBean);
            }

            @Override
            public void error() {
                MToast.show(context, "获取失败!");
                setMainBut(0);
            }
        });
    }

    public void onEvent(StopHeartEvent event) {
        MToast.show(context, "心跳服务已停止");
        setMainBut(0);
    }


    @Click(R.id.mainSubmit)
    public void submit() {
        if (sxAcountEdit.getVisibility() == View.GONE) {
            checkSetting(sxAcountText.getText().toString().toUpperCase(), sxPasswordEdit.getText().toString());

        } else {
            MToast.show(context, "请先保存账号!");
        }
    }

    private void checkSetting(final String sxUser, final String sxPass) {
        if (!CheckUtils.isWifiConnected(context)) {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            MToast.show(context, "请先设置wifi网络");
            return;
        }
        if (!CheckUtils.routerIsValid(context, preference)) {
            RouterSetDialog.instance(context).show();
            return;
        }
        if (!CheckUtils.shanxunIsValid(context, sxUser, sxPass)) {
            return;
        }
        setMainBut(1);
        try {
            mShanxunManager.startDial(context, sxUser, sxPass, new FetchCallback() {
                @Override
                public void get(String result) {
                    if (result.equals("dial_finish")) {
                        setMainBut(2);
                    } else if (result.equals("dial_success")) {
                        if (!preference.getBoolean(Preference.USER_VERIFIED)) {
                            preference.putString(Preference.SX_USER, sxUser);
                            getUserInfo();
                        }
                        setMainBut(3);
                    }
                }

                @Override
                public void error() {
                    setMainBut(4);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    private void setMainBut(int i) {
        switch (i) {
            case 0: {
                mainSubmit.setClickable(true);
                mainSubmit.setText("开始拨号");
                mainSubmit.setBackgroundResource(R.drawable.bg_button_main);
            }
            break;
            case 1: {
                mainSubmit.setClickable(false);
                mainSubmit.setText("正在拨号");
                mainSubmit.setBackgroundResource(R.drawable.bg_button_main_dialing);
            }
            break;
            case 2: {
                mainSubmit.setClickable(false);
                mainSubmit.setText("正在验证网络情况");
                mainSubmit.setBackgroundResource(R.drawable.bg_button_main_dialing);
            }
            break;
            case 3: {
                mainSubmit.setClickable(false);
                mainSubmit.setText("连接成功");
                mainSubmit.setBackgroundResource(R.drawable.bg_button_main_dial_success);
            }
            break;
            case 4: {
                mainSubmit.setClickable(true);
                mainSubmit.setText("连接失败,请重试");
                mainSubmit.setBackgroundResource(R.drawable.bg_button_main_dial_error);
            }
            break;
        }

    }


    private void initPopupWindow() {

        View contentView = getLayoutInflater().inflate(
                R.layout.popupwindow_setting, null);
        RelativeLayout r1 = (RelativeLayout) contentView
                .findViewById(R.id.popup_rl1);
        RelativeLayout r3 = (RelativeLayout) contentView
                .findViewById(R.id.popup_rl3);
        RelativeLayout r4 = (RelativeLayout) contentView
                .findViewById(R.id.popup_rl4);
        popupWindow = new PopupWindow(contentView,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        PopupWindowClick popupWindowClick = new PopupWindowClick(context, popupWindow);
        r1.setOnClickListener(popupWindowClick);
        r3.setOnClickListener(popupWindowClick);
        r4.setOnClickListener(popupWindowClick);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void leftImgClick(ImageView view) {

    }

    @Override
    public void rightImgClick(ImageView view) {
        initPopupWindow();
        popupWindow.showAsDropDown(view, -100, 0);
    }

}
