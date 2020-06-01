package controllers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import data.MemberAnalytics;
import models.Assessment;
import models.Member;
import models.Trainer;
import net.bytebuddy.description.annotation.AnnotationDescription;
import play.Logger;
import play.libs.F;
import play.mvc.Controller;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dasboard");

    Member member = Accounts.getLoggedInMember();
    List<Assessment> assessments = member.assessments;

    // check if there is any assessments, if there is get the latest which is always in position 0
    Assessment assessment = null;
    if (assessments.size() > 0) {
      assessment = assessments.get(0);
    }

    // Call shared utility routines to calculate BMI and determine category and if the current weight is ideal.
    double BMI = (float) healthUtility.calculateBMI(member, assessment);
    String bmicategory = healthUtility.determineBMICategory(BMI);
    boolean idealWeight = healthUtility.isIdealBodyWeight(member, assessment);

    // Rounding BMI to 2 decimal for display
    BMI = Math.round(BMI * 100.0 ) / 100.0;

    // create a new analytics object to hold the BMI, BMI category and ideal weight indicator.
    MemberAnalytics returnAnalytics = new MemberAnalytics(BMI, bmicategory, idealWeight);

    render ("dashboard.html", member, returnAnalytics);
  }


}