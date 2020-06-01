package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public class MemberCtrl extends Controller {
    public static void deleteMember(Long mid) {
        Logger.info("Deleting Member");

        // Check the trainer is currently logged on.
        Trainer trainer = Accounts.getLoggedInTrainer();

        // Retrieve the member to be deleted and the member assessments
        Member member = Member.findById(mid);
        List<Assessment> assessments = member.assessments;

        // Saving in an ArrayList, the ids of the assessments for the member to be deleted
        ArrayList<Long> assessmentids = new ArrayList<>();
        int i=0;
        for (Assessment assessment: assessments) {
            assessmentids.add(i,assessment.id);
            i++;
        }

        // Clear all the member assessments to null
        member.assessments.clear();
        member.save();

        // using the assessment ids find the assessments and delete them one by one
        for (i=0; i < assessmentids.size(); i++) {
            Assessment.findById(assessmentids.get(i))._delete();
        }

        // then we can actually delete the member safely
        member.delete();

        // get an updated list of members to pass to the trainer Dashboard
        List<Member> members = Member.findAllMembers();

        Logger.info("rendering trainerdashboard1");

        render ("trainerdashboard.html", members);
    }
}
