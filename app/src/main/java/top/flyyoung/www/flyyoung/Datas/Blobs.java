package top.flyyoung.www.flyyoung.Datas;

/**
 * Created by 69133 on 2017/1/12.
 */

public class Blobs {

    private int ID;
    private String CreateDate;
    private String CreateTime;
    private String Weather;
    private String BlobImage;
    private String BlobContent;
    private boolean ShowCustomer;

    public void setWeather(String weather) {
        Weather = weather;
    }

    public void setShowCustomer(boolean showCustomer) {
        ShowCustomer = showCustomer;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setBlobImage(String blobImage) {
        BlobImage = blobImage;
    }

    public void setBlobContent(String blobContent) {
        BlobContent = blobContent;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {

        return ID;
    }

    public String getBlobContent() {
        return BlobContent;
    }

    public String getBlobImage() {
        return BlobImage;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getWeather() {
        return Weather;
    }

    public boolean getShowCustomer() {

        return ShowCustomer;
    }

}
