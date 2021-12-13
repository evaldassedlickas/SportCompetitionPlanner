package ktu.edu.sportcompetitionplanner.models;

public class User {
    private String Email;
    private String Username;
    private String Bio;
    private String Uid;

    public User(){}

    public User(String username, String email, String bio, String uid)
    {
        this.Username = username;
        this.Email = email;
        this.Bio = bio;
        this.Uid = uid;
    }

    public String geUsername()
    {
        return Username;
    }

    public void seUsername(String username)
    {
        this.Username = username;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String email)
    {
        this.Email = email;
    }

    public String getBio()
    {
        return Bio;
    }

    public void setBio(String bio)
    {
        this.Bio = bio;
    }

    public String getUid()
    {
        return Uid;
    }

    public void setUid(String uid)
    {
        this.Uid = uid;
    }


}
