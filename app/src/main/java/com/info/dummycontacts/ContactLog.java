package com.info.dummycontacts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by advanz101 on 16/1/18.
 */

public class ContactLog extends SugarRecord {
    @SerializedName("display_name")
    @Expose
    String displayName;
    @SerializedName("name_char")
    @Expose
    String nameChar;
    @SerializedName("contact_number")
    @Expose
    String contactNumber;
    @SerializedName("photo_uri")
    @Expose
    String photoUri;
    @SerializedName("email_address")
    @Expose
    String emailAddress;
    @SerializedName("birth_day")
    @Expose
    long birthDay;
    @SerializedName("last_contact_on")
    @Expose
    long lastContactOn;

    @SerializedName("frequency")
    @Expose
    long frequency;
    @SerializedName("communication_type")
    @Expose
    String communicationType;


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNameChar() {
        return nameChar;
    }

    public void setNameChar(String nameChar) {
        this.nameChar = nameChar;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public long getLastContactOn() {
        return lastContactOn;
    }

    public void setLastContactOn(long lastContactOn) {
        this.lastContactOn = lastContactOn;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public String getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(String communicationType) {
        this.communicationType = communicationType;
    }
}
