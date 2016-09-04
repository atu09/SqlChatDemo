package alm.com.sqlchatdemo.RowItems;

/**
 * Created by alivemind on 16/07/16.
 */
public class ChatItem {

    String with;
    String msgContent;
    String timeStamp;
    String userType;
    String msgType;

    public ChatItem(String with, String msgContent, String timeStamp, String userType, String msgType) {
        this.with = with;
        this.msgContent = msgContent;
        this.timeStamp = timeStamp;
        this.userType = userType;
        this.msgType = msgType;
    }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
