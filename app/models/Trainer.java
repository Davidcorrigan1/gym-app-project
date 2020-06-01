package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Trainer extends Model {
    public String trainerName;
    public String email;
    public String password;

    public Trainer(String trainerName, String email, String password) {
        this.trainerName = trainerName;
        this.email = email;
        this.password = password;
    }

    public static Trainer findByEmail(String email)
    {

        return find("email", email).first();
    }

    public boolean checkPassword(String password)
    {

        return this.password.equals(password);
    }
}
