package top.flyyoung.www.flyyoung.Datas;

/**
 * Created by 69133 on 2017/1/13.
 * Album/Get?id=10
 */

public class Photo {

    private int ID;
    private int AlbumID;
    private Object Album;
    private String PhotoName;
    private String  PhotoDescription;
    private String  CreateDate;
    private String  CreateTime;
    private  Boolean ShowCustomer;
    private String PhotoUrl;

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }

    public void setShowCustomer(Boolean showCustomer) {
        ShowCustomer = showCustomer;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public void setPhotoName(String photoName) {
        PhotoName = photoName;
    }

    public void setPhotoDescription(String photoDescription) {
        PhotoDescription = photoDescription;
    }

    public void setAlbum(Object album) {
        Album = album;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Boolean getShowCustomer() {
        return ShowCustomer;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public int getID() {
        return ID;
    }

    public Object getAlbum() {
        return Album;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getPhotoDescription() {
        return PhotoDescription;
    }

    public String getPhotoName() {
        return PhotoName;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }
}
