package us.idinfor.hemophilia.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
public class Infusion {

    @Id
    Long id;
    @Index
    String deviceId;
    @Index
    String medication;
    Integer dose;
    String lotNumber;
    @Index
    Date time;
    String description;

    public Infusion() {
    }

    public Infusion(String deviceId, String medication, Integer dose, String lotNumber, Date time, String description) {
        this.deviceId = deviceId;
        this.medication = medication;
        this.dose = dose;
        this.lotNumber = lotNumber;
        this.time = time;
        this.description = description;
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
}
