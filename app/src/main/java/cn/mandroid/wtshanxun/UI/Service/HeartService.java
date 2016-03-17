package cn.mandroid.wtshanxun.UI.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.RemoteViews;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.Receiver;

import cn.mandroid.wtshanxun.event.TotalEvent;
import cn.mandroid.wtshanxun.Model.HeartManager;
import cn.mandroid.wtshanxun.R;
import cn.mandroid.wtshanxun.UI.activity.MainActivity;
import cn.mandroid.wtshanxun.event.StopHeartEvent;
import cn.mandroid.wtshanxun.utils.Udp;
import de.greenrobot.event.EventBus;

@EIntentService
public class HeartService extends IntentService {
    public static Context context;
    NotificationManager nm;
    Notification.Builder notificationBuilder;
    Notification mNotification;
    RemoteViews contentView;
    private PendingIntent pd;
    private int nid = 1234;
    PowerManager.WakeLock wakeLock;
    @Bean
    HeartManager mHeartManager;

    public HeartService() {
        super("heart");
    }

    public static EventBus eventBus;
    private int total;
    private int successCount;

    @Override
    public void onCreate() {
        super.onCreate();
        eventBus = new EventBus();
        eventBus.register(this);
        total=0;
        successCount=0;
        setWake();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initNotifi();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true) {
            mHeartManager.sendHeart(this);
            total++;
            try {
                Thread.sleep(1000 * 60 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void initNotifi() {
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        contentView = new RemoteViews(getPackageName(),
                R.layout.notification_heart);
        Intent intent = new Intent("onNotifButClick");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, 0);
        PendingIntent pd = PendingIntent.getActivity(this, 0, new Intent(this,
                MainActivity.class), 0);
        contentView.setOnClickPendingIntent(R.id.exitHeartBut, pendingIntent);
        notificationBuilder = new Notification.Builder(this).setAutoCancel(false).setSmallIcon(R.drawable.ic_launcher).setWhen(System.currentTimeMillis()).setContent(contentView).setContentIntent(pd);
        mNotification = notificationBuilder.getNotification();
        mNotification.flags |= Notification.FLAG_NO_CLEAR;
        nm.notify(nid, mNotification);
    }

    private void setWake() {
        PowerManager pm = (PowerManager) getApplication().getSystemService(Context.POWER_SERVICE);
        // 保持cpu一直运行，不管屏幕是否黑屏
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "CPUKeepRunning");
        wakeLock.acquire();
    }

    public void onEvent(TotalEvent e) {
        contentView.setTextViewText(R.id.totalText, "已发送" + total + "次,成功" + (++successCount) + "次");
        mNotification.contentView = contentView;
        nm.notify(nid, mNotification);
    }

    public void onEvent(StopHeartEvent event) {
        stopSelf();
    }

    public void onEventAsync(Udp udp) {
        udp.send();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        nm.cancel(nid);
        if (wakeLock != null) {
            wakeLock.release();
        }
        eventBus.unregister(this);
        EventBus.getDefault().post(new StopHeartEvent());
        super.onDestroy();
    }

    @Receiver(actions = "onNotifButClick")
    protected void notifButClick() {
        stopSelf();
    }
}
