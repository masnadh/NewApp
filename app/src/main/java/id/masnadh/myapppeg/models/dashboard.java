package id.masnadh.myapppeg.models;

import android.os.Parcel;
import android.os.Parcelable;

public class dashboard implements Parcelable {

    private int img;
    private String title;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.img);
        dest.writeString(this.title);
    }

    public dashboard() {
    }

    protected dashboard(Parcel in) {
        this.img = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<dashboard> CREATOR = new Parcelable.Creator<dashboard>() {
        @Override
        public dashboard createFromParcel(Parcel source) {
            return new dashboard(source);
        }

        @Override
        public dashboard[] newArray(int size) {
            return new dashboard[size];
        }
    };
}
