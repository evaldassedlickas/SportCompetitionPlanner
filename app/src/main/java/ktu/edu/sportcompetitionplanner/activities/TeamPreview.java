package ktu.edu.sportcompetitionplanner.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ktu.edu.sportcompetitionplanner.R;
import ktu.edu.sportcompetitionplanner.models.Player;
import ktu.edu.sportcompetitionplanner.models.Team;

public class TeamPreview extends Base {
    private TextView tvName, tvCountry, tvDescription;
    //private RecyclerView recyclerTeamPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_preview);
        subtitle = "Team details";

        tvName = findViewById(R.id.tvNameTeamPreview);
        tvCountry = findViewById(R.id.tvCountryTeamPreview);
        tvDescription = findViewById(R.id.tvDescriptionTeamPreview);
       // recyclerTeamPlayers = findViewById(R.id.rvTeamPlayersTeamPreview);

        Intent intent = getIntent();
        Team team = intent.getParcelableExtra("teamInfo");

        tvName.setText(team.getname());
        tvCountry.setText(team.getcountry());
        tvDescription.setText(team.getdescription());

    }
}