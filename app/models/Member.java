package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Entity
public class Member extends Model
{
    public String memberName;
    public String gender;
    public String email;
    public String password;
    public String address;
    public float height;
    public float startingWeight;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessment> assessments = new ArrayList<Assessment>();

    public Member(String memberName, String gender, String email, String password, String address, float height, float startingWeight) {
        this.memberName = memberName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.address = address;
        this.height = height;
        this.startingWeight = startingWeight;
    }

    public static Member findByEmail(String email)
    {

        return find("email", email).first();
    }

    public static List<Member> findAllMembers() {
        return findAll();
    }

    public boolean checkPassword(String password)
    {

        return this.password.equals(password);
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setStartingWeight(float startingWeight) {
        this.startingWeight = startingWeight;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public float getHeight() {
        return height;
    }

    public float getStartingWeight() {
        return startingWeight;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

}