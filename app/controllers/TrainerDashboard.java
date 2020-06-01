package controllers;

import data.MemberAnalytics;
import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public class TrainerDashboard extends Controller {
    public static void index() {
        Logger.info("Rendering Trainer Dasboard");

        Trainer trainer = Accounts.getLoggedInTrainer();

        List<Member> members = Member.findAllMembers();

        render ("trainerdashboard.html", members);
    }

    public static void displayMember(Long id) {
        Logger.info("Rendering Trainer Member Dasboard");

        // Get logged in Trainer. And find passed in member id.
        Trainer trainer = Accounts.getLoggedInTrainer();
        Member member = Member.findById(id);

        List<Assessment> assessments = member.assessments;

        // check if there is any assessments, if there is get the latest which is always in position 0
        Assessment assessment = null;
        if (assessments.size() > 0) {
            assessment = assessments.get(0);
        }

        // Call shared utility routines to calculate BMI and determine category
        double BMI = (float) healthUtility.calculateBMI(member, assessment);
        String bmicategory = healthUtility.determineBMICategory(BMI);
        boolean idealWeight = healthUtility.isIdealBodyWeight(member, assessment);

        // Rounding BMI to 2 decimal for display
        BMI = Math.round(BMI * 100.0 ) / 100.0;

        // create a new analytics object to hold the BMI, BMI category and ideal weight indicator.
        MemberAnalytics returnAnalytics = new MemberAnalytics(BMI, bmicategory, idealWeight);

        render ("memberdashboard.html", member, returnAnalytics);
    }


}
