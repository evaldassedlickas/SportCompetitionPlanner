package ktu.edu.sportcompetitionplanner.models;
import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable {
    private String name;
    private String country;
    private String description;
    private String uId;

    public Team(){}

    public Team(String name, String country, String description, String uId)
    {
        this.name = name;
        this.country = country;
        this.description = description;
        this.uId = uId;
    }

    protected Team(Parcel in)
    {
        name = in.readString();
        country = in.readString();
        description = in.readString();
        uId = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getname()
    {
        return name;
    }
    public void setname(String name)
    {
        this.name = name;
    }

    public String getcountry()
    {
        return country;
    }
    public void setcountry(String country)
    {
        this.country = country;
    }

    public String getdescription()
    {
        return description;
    }
    public void setdescription(String description)
    {
        this.description = description;
    }


    public String getuId() { return this.uId; }
    public void setuId(String uId){ this.uId = uId; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(country);
        dest.writeString(description);
        dest.writeString(uId);
    }
}
