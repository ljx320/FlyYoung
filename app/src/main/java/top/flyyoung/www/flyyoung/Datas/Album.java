package top.flyyoung.www.flyyoung.Datas;

/**
 * Created by 69133 on 2017/1/13.
 * Album/get
 */

public class Album {
    private int ID;
    private String AlbumName;
    private String AlbumDescription;
    private int  AlbumSuject;
    private boolean ShowCustomer;
    private int Flags;
    private String CreateDate;
    private String  CreateTime;
    private  Object Photos;
    private int PhotosCount;
    private String FirstShow;


    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setShowCustomer(boolean showCustomer) {
        ShowCustomer = showCustomer;
    }

    public void setPhotosCount(int photosCount) {
        PhotosCount = photosCount;
    }

    public void setPhotos(Object photos) {
        Photos = photos;
    }

    public void setFlags(int flags) {
        Flags = flags;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFirstShow(String firstShow) {
        FirstShow = firstShow;
    }

    public void setAlbumSuject(int albumSuject) {
        AlbumSuject = albumSuject;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public void setAlbumDescription(String albumDescription) {
        AlbumDescription = albumDescription;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getFirstShow() {
        return FirstShow;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public String getAlbumDescription() {
        return AlbumDescription;
    }

    public Object getPhotos() {
        return Photos;
    }

    public int getPhotosCount() {
        return PhotosCount;
    }

    public int getAlbumSuject() {
        return AlbumSuject;
    }

    public int getFlags() {
        return Flags;
    }

    public int getID() {
        return ID;
    }

    public boolean getShowCustomer() {
        return ShowCustomer;
    }
}
