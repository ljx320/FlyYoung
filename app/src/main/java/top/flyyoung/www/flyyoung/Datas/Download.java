package top.flyyoung.www.flyyoung.Datas;

/**
 * Created by 69133 on 2017/1/13.
 * Download/get
 * Download/Get?fileName=/Files/20170116/test.txt
 * Download/PostFile
 * Download/Post
 */

public class Download {

    private  int ID;
    private String FileName;
    private String  FileVersion;
    private Boolean  ShowCustomer;
    private String  CreateDate;
    private String  CreateTime;
    private String  FileDescription;
    private String  UseDescription;
    private String  FilePath;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setUseDescription(String useDescription) {
        UseDescription = useDescription;
    }

    public void setShowCustomer(Boolean showCustomer) {
        ShowCustomer = showCustomer;
    }

    public void setFileVersion(String fileVersion) {
        FileVersion = fileVersion;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public void setFileDescription(String fileDescription) {
        FileDescription = fileDescription;
    }

    public Boolean getShowCustomer() {
        return ShowCustomer;
    }

    public int getID() {
        return ID;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getFileDescription() {
        return FileDescription;
    }

    public String getFileName() {
        return FileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public String getFileVersion() {
        return FileVersion;
    }

    public String getUseDescription() {
        return UseDescription;
    }
}
