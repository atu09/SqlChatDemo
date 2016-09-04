package alm.com.sqlchatdemo.RowItems;

/**
 * Created by alivemind on 16/07/16.
 */
public class InboxItem {

    String userName;
    String contactTime;

    public InboxItem(String userName, String contactTime) {
        this.userName = userName;
        this.contactTime = contactTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactTime() {
        return contactTime;
    }

    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }
}
