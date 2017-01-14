package top.flyyoung.www.flyyoung.Datas;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 69133 on 2017/1/13.
 * Blob/PostImage
 */

public class UploadFileResult implements Parcelable {

    private String  Name;
    private String  Url;
    private int  Size;

    public String getName() {
        return Name;
    }

    public int getSize() {
        return Size;
    }

    public String getUrl() {
        return Url;
    }

    public void setName(String name) {
        Name = name;


    }

    public void setSize(int size) {
        Size = size;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.Url);
        dest.writeInt(this.Size);
    }

    public UploadFileResult() {
    }

    protected UploadFileResult(Parcel in) {
        this.Name = in.readString();
        this.Url = in.readString();
        this.Size = in.readInt();
    }

    public static final Parcelable.Creator<UploadFileResult> CREATOR = new Parcelable.Creator<UploadFileResult>() {
        @Override
        public UploadFileResult createFromParcel(Parcel source) {
            return new UploadFileResult(source);
        }

        @Override
        public UploadFileResult[] newArray(int size) {
            return new UploadFileResult[size];
        }
    };
}
