package top.flyyoung.www.flyyoung.Datas;

/**
 * Created by 69133 on 2017/1/13.
 * Blob/PostImage
 */

public class UploadFileResult {

    private String  Name;
    private String  Url;
    private String  Size;

    public String getName() {
        return Name;
    }

    public String getSize() {
        return Size;
    }

    public String getUrl() {
        return Url;
    }

    public void setName(String name) {
        Name = name;


    }

    public void setSize(String size) {
        Size = size;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
