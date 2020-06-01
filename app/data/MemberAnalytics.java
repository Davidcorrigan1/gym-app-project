package data;

public class MemberAnalytics {

    double BMI;
    String bmicategory;
    boolean idealWeight;

    public MemberAnalytics(double BMI, String bmicategory, boolean idealWeight) {
        this.BMI = BMI;
        this.bmicategory = bmicategory;
        this.idealWeight = idealWeight;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public String getBmicategory() {
        return bmicategory;
    }

    public void setBmicategory(String bmicategory) {
        this.bmicategory = bmicategory;
    }

    public boolean isIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(boolean idealWeight) {
        this.idealWeight = idealWeight;
    }
}
