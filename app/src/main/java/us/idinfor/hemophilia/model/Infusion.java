package us.idinfor.hemophilia.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.client.util.DateTime;

import java.util.Date;

public class Infusion implements Parcelable{

    Long id;
    String deviceId;
    String medication;
    Integer dose;
    String lotNumber;
    Date time;
    String description;

    public Infusion() {
    }

    public Infusion(us.idinfor.hemophilia.backend.infusionApi.model.Infusion infusion){
        this.id = infusion.getId();
        this.deviceId = infusion.getDeviceId();
        this.medication = infusion.getMedication();
        this.dose = infusion.getDose();
        this.lotNumber = infusion.getLotNumber();
        this.time = new Date(infusion.getTime().getValue());
        this.description = infusion.getDescription();
    }

    public us.idinfor.hemophilia.backend.infusionApi.model.Infusion getBackendInfusion(){
        us.idinfor.hemophilia.backend.infusionApi.model.Infusion backendInfusion = new us.idinfor.hemophilia.backend.infusionApi.model.Infusion();
        backendInfusion.setId(this.getId());
        backendInfusion.setDeviceId(this.getDeviceId());
        backendInfusion.setMedication(this.getMedication());
        backendInfusion.setDose(this.getDose());
        backendInfusion.setLotNumber(this.getLotNumber());
        backendInfusion.setTime(new DateTime(this.getTime()));
        backendInfusion.setDescription(this.getDescription());
        return backendInfusion;
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
        dest.writeString(this.medication);
        dest.writeValue(this.dose);
        dest.writeString(this.lotNumber);
        dest.writeLong(time != null ? time.getTime() : -1);
        dest.writeString(this.description);
    }



    protected Infusion(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.deviceId = in.readString();
        this.medication = in.readString();
        this.dose = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lotNumber = in.readString();
        long tmpTime = in.readLong();
        this.time = tmpTime == -1 ? null : new Date(tmpTime);
        this.description = in.readString();
    }

    public static final Creator<Infusion> CREATOR = new Creator<Infusion>() {
        public Infusion createFromParcel(Parcel source) {
            return new Infusion(source);
        }

        public Infusion[] newArray(int size) {
            return new Infusion[size];
        }
    };
}
