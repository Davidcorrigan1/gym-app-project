package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AssessmentCtrl extends Controller
{
  /*
  This method will delete an assessment for a member. It is passed in the member id and
  the assessment id to be deleted. It then retrieves the member from the DB using the id and also
  retrieves the assessment from the DB using the ID.
  It then removed the assessment from the member assessments array.
  It then saves the member details (i.e. minus the assessment).
  It then deletes the assessment itself from the DB.
  An finally re-displays the Member Dashboard.
   */
  public static void deleteAssessment (Long id, Long assessmentid)
  {
    Member member = Member.findById(id);
    Assessment assessment = Assessment.findById(assessmentid);
    Logger.info ("Removing Assessment" + assessment.id);
    member.assessments.remove(assessment);
    member.save();
    assessment.delete();
    Dashboard.index();
  }

  /*
  This method will add a new assessment for a member. The assessment details are passed in and then need to be
  parsed from String to float. If any mandatory value is not greater than zero the assessment is not added.
  It calls a utility method checkIfWeightIncrease() to see if this assessment weight is greater or less than the
  previous assessment or if no assessments the starting weight.
  It then create a new Assessment object using this and the passed in data.
  It will add this assessment to the member assessments array in position 0. This means the latest assessment is
  always in position 0, and the array in sequences in array order.
  It calls static method getCurrentDateTime() to return the date and time as a String to populate the first parameter
  in the constructor. It then saves the assessment and then saves the member.

   */
  public static void addAssessment (String weight, String chest, String thigh, String upperArm, String waist, String hips)
  {
    try {
      float fweight = Float.parseFloat(weight);
      float fchest = Float.parseFloat(chest);
      float fthigh = Float.parseFloat(thigh);
      float fupperArm = Float.parseFloat(upperArm);
      float fwaist = Float.parseFloat(waist);
      float fhips = Float.parseFloat(hips);
      Logger.info("Adding a Assessment");
      Member member = Accounts.getLoggedInMember();

      if (fweight > 0 && fchest > 0 && fthigh > 0 && fupperArm > 0 && fwaist > 0 && fhips > 0){
        int weightIncrease = healthUtility.checkIfWeightIncrease(member, fweight);
        Assessment assessment = new Assessment(getCurrentDateTime(), fweight, fchest, fthigh, fupperArm, fwaist, fhips,weightIncrease);
        member.assessments.add(0, assessment);
        assessment.save();
        member.save();
      } else {
        redirect ("/dashboard");
      }

    } catch (Exception e) {
      redirect ("/dashboard");
    }
    redirect ("/dashboard");
  }

  /*
  This method will take in a member id and an assessment id and a comment to be added to the assessment.
  It checks that there is a logged in trainer first for security.
  It then retrieves the member and assessment from the DB using the ids.
  It uses the assessment setter to updater the comment.
  It then saves the assessment,  And redisplay the trainer-member dashboard.
   */
  public static void addComment(Long mid, Long aid, String comment) {
    Logger.info("Adding Member Comment");

    Logger.info("member id: " + mid);
    Logger.info("assess id: " + aid);
    Logger.info("comment: " + comment);

    Trainer trainer = Accounts.getLoggedInTrainer();

    Member member = Member.findById(mid);
    Assessment updateAssessment = Assessment.findById(aid);

    updateAssessment.setComment(comment);

    updateAssessment.save();

    redirect ("/trainerdashboard/member/" + member.id);

  }

  /*
  This ZoneId for GMT. This gets passed into method now() to get the current date/time as a ZonedDateTime object.
  It then sets up a pattern for the date/time and uses the format() method to format this as a String.
   */
  public static String getCurrentDateTime() {
    ZoneId z = ZoneId.of("GMT");
    ZonedDateTime currentDate = ZonedDateTime.now(z);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    String formattedDate = currentDate.format(formatter);
    return formattedDate;
  }
}
