package us.idinfor.hemophilia.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.DateTime;

import java.util.Date;

public class Bleed implements Parcelable {

    Long id;
    String deviceId;
    String reason;
    String severity;
    String bodyPart;
    String medication;
    Integer dose;
    String lotNumber;
    Date time;
    String description;


    public Bleed() {
    }


    public Bleed(us.idinfor.hemophilia.backend.bleedApi.model.Bleed bleed){
        this.id = bleed.getId();
        this.deviceId = bleed.getDeviceId();
        this.medication = bleed.getMedication();
        this.dose = bleed.getDose();
        this.lotNumber = bleed.getLotNumber();
        this.time = new Date(bleed.getTime().getValue());
        this.description = bleed.getDescription();
        this.reason = bleed.getReason();
        this.severity = bleed.getSeverity();
        this.bodyPart = bleed.getBodyPart();
    }

    public us.idinfor.hemophilia.backend.bleedApi.model.Bleed getBackendBleed(){
        us.idinfor.hemophilia.backend.bleedApi.model.Bleed backendBleed = new us.idinfor.hemophilia.backend.bleedApi.model.Bleed();
        backendBleed.setId(this.getId());
        backendBleed.setDeviceId(this.getDeviceId());
        backendBleed.setMedication(this.getMedication());
        backendBleed.setDose(this.getDose());
        backendBleed.setLotNumber(this.getLotNumber());
        backendBleed.setTime(new DateTime(this.getTime()));
        backendBleed.setDescription(this.getDescription());
        backendBleed.setReason(this.getReason());
        backendBleed.setSeverity(this.getSeverity());
        backendBleed.setBodyPart(this.getBodyPart());

        return backendBleed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public Integer getDose() {
        return dose;
    }

    public void setDose(Integer dose) {
        this.dose = dose;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.deviceId);
        dest.writeString(this.reason);
        dest.writeString(this.severity);
        dest.writeString(this.bodyPart);
        dest.writeString(this.medication);
        dest.writeValue(this.dose);
        dest.writeString(this.lotNumber);
        dest.writeLong(time != null ? time.getTime() : -1);
        dest.writeString(this.description);
    }

    protected Bleed(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.deviceId = in.readString();
        this.reason = in.readString();
        this.severity = in.readString();
        this.bodyPart = in.readString();
        this.medication = in.readString();
        this.dose = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lotNumber = in.readString();
        long tmpTime = in.readLong();
        this.time = tmpTime == -1 ? null : new Date(tmpTime);
        this.description = in.readString();
    }

    public static final Creator<Bleed> CREATOR = new Creator<Bleed>() {
        public Bleed createFromParcel(Parcel source) {
            return new Bleed(source);
        }

        public Bleed[] newArray(int size) {
            return new Bleed[size];
        }
    };


}
