package com.example.luqis_.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable{
    private String name, phone, password, image, address;
    public Users()
    {

    }

    public Users(String name, String phone, String password, String image, String address) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeString(this.password);
    }

    protected Users(Parcel in){
        this.phone = in.readString();
        this.name = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>(){
        @Override
        public Users createFromParcel(Parcel source){
            return new Users(source);
        }

        @Override
        public Users[] newArray(int size){
            return new Users[size];
        }
    };
}
