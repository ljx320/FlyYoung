package top.flyyoung.www.flyyoung.Datas;

/**
 * Created by 69133 on 2017/1/13.
 * Message/get
 */

public class Message {
    private int  ID;
    private String IPAddress;
    private String  CreateDate;
    private  String CreateTime;
    private String MessageContent;

    public String getCreateTime() {
        return CreateTime;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public int getID() {
        return ID;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }
}
