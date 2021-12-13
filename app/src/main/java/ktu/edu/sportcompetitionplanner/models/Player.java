package ktu.edu.sportcompetitionplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private String firstname;
    private String lastname;
    private String position;
    //private String team;
    private String photoUrl;
    private String uId;

    public Player(){}

    public Player(String firstname, String lastname, String position, String photoUrl, String uId)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.position = position;
      // this.Team = team;
        this.photoUrl = photoUrl;
        this.uId = uId;
    }

    protected Player(Parcel in)
    {
        firstname = in.readString();
        lastname = in.readString();
        position = in.readString();
        photoUrl = in.readString();
        uId = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getfirstname()
    {
        return firstname;
    }
    public void setfirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getlastname()
    {
        return lastname;
    }
    public void setlastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getposition()
    {
        return position;
    }
    public void setposition(String position)
    {
        this.position = position;
    }

/*    public String getTeam()
    {
        return Team;
    }
    public void setTeam(String team)
    {
        this.Team = team;
    }*/

    public String getphotoUrl()
    {
        return photoUrl;
    }
    public void setphotoUrl(String photoUrl)
    {
        this.photoUrl = photoUrl;
    }

    public String getuId() { return this.uId; }
    public void setuId(String uId){ this.uId = uId; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(position);
        dest.writeString(photoUrl);
        dest.writeString(uId);
    }
}
