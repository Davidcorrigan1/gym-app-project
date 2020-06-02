package controllers;

import models.Assessment;
import models.Member;
import play.Logger;

public class healthUtility {
    /*
    This method calculates the BMI for the member passed in. If there are is an assessment
    passed in then it uses the weight from that. Otherwise it will use the members starting
    weight.
     */
    public static double calculateBMI(Member member, Assessment assessment) {
        double BMI=0;
        if (assessment == null){
            Logger.info("Calculating BMI Starting" + member.startingWeight);
            Logger.info("Member Height" + member.height);
            BMI = (member.startingWeight / (Math.pow(member.height, 2.0)));
            Logger.info("BMI : " + BMI);
        } else {
            Logger.info("Calculating BMI Assessment" + assessment.weight);
            Logger.info("Member Height" + member.height);
            BMI = (assessment.weight / (Math.pow(member.height, 2.0)));
        }
        Logger.info("BMI : " + BMI);
        return BMI;
    }

    public static String determineBMICategory(double bmiValue) {
        /*
        Returns the category the BMI belongs to, based on the following values:
        BMI less than 16 (exclusive) is "SEVERELY UNDERWEIGHT"
        BMI between 16 (inclusive) and 18.5 (exclusive) is "UNDERWEIGHT"
        BMI between 18.5 (inclusive) and 25(exclusive) is "NORMAL"
        BMI between 25 (inclusive) and 30 (exclusive) is "OVERWEIGHT"
        BMI between 30 (inclusive) and 35 (exclusive) is "MODERATELY OBESE"
        BMI greater than 35 (inclusive) and is "SEVERELY OBESE"
         */

        if (bmiValue < 16) {
            return "SEVERELY UNDERWEIGHT";
        } else if (bmiValue >= 16 && bmiValue < 18.5) {
            return "UNDERWEIGHT";
        } else if (bmiValue >= 18.5 && bmiValue < 25) {
            return "NORMAL";
        } else if (bmiValue >= 25 && bmiValue < 30) {
            return "OVERWEIGHT";
        } else if (bmiValue >= 30 && bmiValue < 35) {
            return "MODERATELY OBESE";
        } else {
            return "SEVERELY OBESE";
        }
    }

    /**
     * This method takes in a Member object and the members latest assessment.
     * It uses the Devine formula to find the members ideal weight based on gender and height.
     * It then compares this ideal weight to the members latest assessment weight to determine if the
     * member has an ideal weight. It returns true if the member has an ideal weight otherwise false.
     * The ideal weight is calculated with a tolerance of 5kgs.
     * @param member
     * @param assessment
     * @return boolean true or false depending on if the member as an ideal weight.
     */
    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {
        double idealWeight;
        if (member.getGender().toLowerCase().equals("male") || member.getGender().toLowerCase().equals("m")) {
            idealWeight = (50 + (2.3 * (convertMetersToInches(member.getHeight()) - 60)));
        } else {
            idealWeight = (45.5 + (2.3 * (convertMetersToInches(member.getHeight()) - 60)));
        }

        if (assessment == null) {
            if (Math.abs(member.startingWeight) - idealWeight < 2.0) {
                return true;
            }
        } else {
            if (Math.abs(assessment.getWeight() - idealWeight) < 2.0) {
                return true;
            }
        }
        return false;
    }

    /*
    This method will take in a member and a new weight in kgs. If the member has assessments
    the it uses the latest assessment (always index 0) to compare against the new weight.
    If there are no assessments as yet, then it will compare the new weight to the member
    starting weight.
    It returns an int, either:
    +1 when there is a weight increase
    -1 when there is a weight decrease
     0 when there is no change
     */
    public static int checkIfWeightIncrease(Member member, float newWeight) {
        if (member.assessments.isEmpty()) {
            if (member.startingWeight < newWeight) {
                return +1;
            } else if (member.startingWeight > newWeight){
                return -1;
            } else {
                return 0;
            }
        } else {
            if (member.assessments.get(0).weight < newWeight) {
                return +1;
            } else if (member.assessments.get(0).weight > newWeight){
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Converts height in Meters to height in Inches
     * @param heightMeters
     * @return height in inches
     */
    public static double convertMetersToInches(double heightMeters) {

        return (heightMeters * 39.37);
    }

    /**
     * Converts weight in Kgs to weight in pounds
     * @param kilograms
     * @return weight in lbs.
     */
    public static double convertKilogramsToPounds(double kilograms) {

        return (kilograms * 2.20462);
    }



}
