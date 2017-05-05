package hw2.tim.ko.contactmanagerhw2bychin_tingko;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TimKo on 6/5/16.
 */
public class ContactItem implements Parcelable {
    private long id;
    private String firstName;
    private String lastName;
    private String homeNumber;
    private String workNumber;
    private String mobileNumber;
    private String email;

    public ContactItem() {}
    public ContactItem(long id, String firstName, String lastName, String homeNumber, String workNumber, String mobileNumber, String email) {
        this.id= id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeNumber = homeNumber;
        this.workNumber = workNumber;
        this.mobileNumber = mobileNumber;
        this.email = email;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(homeNumber);
        dest.writeString(workNumber);
        dest.writeString(mobileNumber);
        dest.writeString(email);
    }

    public static Creator<ContactItem> CREATOR = new Creator<ContactItem>() {

        @Override
        public ContactItem createFromParcel(Parcel source) {
            ContactItem contactItem = new ContactItem();
            contactItem.setFirstName(source.readString());
            contactItem.setLastName(source.readString());
            contactItem.setHomeNumber(source.readString());
            contactItem.setWorkNumber(source.readString());
            contactItem.setMobileNumber(source.readString());
            contactItem.setEmail(source.readString());
            return contactItem;
        }

        @Override
        public ContactItem[] newArray(int size) {
            return new ContactItem[size];
        }
    };
}
