package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Assessment extends Model {
    public String dateTime;
    public float weight;
    public float chest;
    public float thigh;
    public float upperArm;
    public float waist;
    public float hips;
    public String comment;
    public int weightIncrease;
    ;

    public Assessment(String dateTime, float weight, float chest, float thigh, float upperArm, float waist, float hips, int weightIncrease) {
        this.dateTime = dateTime;
        this.weight = weight;
        this.chest = chest;
        this.thigh = thigh;
        this.upperArm = upperArm;
        this.waist = waist;
        this.hips = hips;
        this.comment = "";
        this.weightIncrease = weightIncrease;
    }

    public String getDateTime(){
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getChest() {
        return chest;
    }

    public void setChest(float chest) {
        this.chest = chest;
    }

    public float getThigh() {
        return thigh;
    }

    public void setThigh(float thigh) {
        this.thigh = thigh;
    }

    public float getUpperArm() {
        return upperArm;
    }

    public void setUpperArm(float upperArm) {
        this.upperArm = upperArm;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public float getHips() {
        return hips;
    }

    public void setHips(float hips) {
        this.hips = hips;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int isWeightIncrease() {
        return weightIncrease;
    }

    public void setWeightIncrease(int weightIncrease) {
        this.weightIncrease = weightIncrease;
    }
}
