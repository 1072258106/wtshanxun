package cn.mandroid.wtshanxun.Model.Bean;

import java.io.Serializable;

/**
 * Created by wangtao on 2016-04-13.
 */
public class NoticeBean extends BasicBean{
    private String title;
    private String content;
    private String linkText;
    private String url;

    public String getTitle() {
        return title==null?"":title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
