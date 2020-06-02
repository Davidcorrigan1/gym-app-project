package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller
{
    public static void signup()
    {
        render("signup.html");
    }
    /*
    Renders the login page
     */
    public static void login()
    {
        render("login.html");
    }

    /*
    This method registers a new user. It first checks that the user has populated all the essential fields
    and then creates new member object using the data and then uses method save to save to the database.
     */
    public static void register(String memberName, String gender, String email, String password, String address, float height, float startingWeight) {

        if (!memberName.isEmpty() && !gender.isEmpty() && !email.isEmpty() && !password.isEmpty() && height > 0 && startingWeight > 0)
        {
            Logger.info("Registering new user " + email);
            Member member = new Member(memberName, gender, email.toLowerCase(), password, address, height, startingWeight);
            member.save();
            redirect("/");
        } else {
            redirect("/signup");
        }

    }

    /*
    This method renders the new settings page to update users settings.
     */
    public static void settings() {
        render("settings.html");
    }

    /*
    This method accepts the input passed from the update setting form. It returns the logged in member,
    and then depending on the data passed in will use the setters to update hte member object before
    saving to the DB.
     */
    public static void settingsUpdate(String memberName, String gender, String email, String password, String address, float height, float startingWeight)
    {
        Member member = Accounts.getLoggedInMember();

        Logger.info("Updating Settings " + email);
        if(!memberName.isEmpty()) {
            member.setMemberName(memberName);
        }
        if (!gender.isEmpty()) {
            member.setGender(gender);
        }
        if (!email.isEmpty()) {
            member.setEmail(email.toLowerCase());
        }
        if (!password.isEmpty()){
            member.setPassword(password);
        }
        if (!address.isEmpty()){
            member.setAddress(address);
        }
        if(!(height<=0)) {
            member.setHeight(height);
        }
        if (!(startingWeight<=0)) {
            member.setStartingWeight(startingWeight);
        }

        member.save();
        redirect ("/dashboard");
    }

    /*
    This method authenticates a user using the email and password. It search both the member and
    trainer databases using the e-mail. It then checks both objects to see if one of them is found.
    Whichever one is found, the password passed in is check against the password of the object returned.
    It will trigger a different dashboard depending on if it's a trainer or member logging in.
     */
    public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email.toLowerCase());
        Trainer trainer = Trainer.findByEmail(email.toLowerCase());
        if ((member != null) && member.checkPassword(password)) {
            Logger.info("Member Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        } else if (trainer != null && trainer.checkPassword(password)) {
            Logger.info("Trainer Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect ("/trainerdashboard");
        } else {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }

    /*
    This logs out a user by clearing the session details.
     */
    public static void logout()
    {
        session.clear();
        redirect ("/");
    }

    /*
    This method checks which member is logged in. It creates a null Member object first.
    Then it checks the session to see if there is a logged in member.
    If there is then it gets the member id, and uses it to retrieve the member detail from the DB.
    Otherwise it throws up the login menu.
     */
    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }

    /*
   This method checks which trainer is logged in. It creates a null Trainer object first.
   Then it checks the session to see if there is a logged in trainer.
   If there is then it gets the trainer id, and uses it to retrieve the trainer detail from the DB.
   Otherwise it throws up the login menu.
    */
    public static Trainer getLoggedInTrainer()
    {
        Logger.info("check trainer logged on");
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            Logger.info("forcing Login");
            login();
        }
        return trainer;
    }
}
