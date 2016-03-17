package cn.mandroid.wtshanxun.event;

/**
 * Created by Administrator on 2015-11-26.
 */
public class TotalEvent {
    public  int getSendHeartCount() {
        return sendHeartCount;
    }

    public void setSendHeartCount() {
        sendHeartCount++;
    }

    private static int sendHeartCount;

}
