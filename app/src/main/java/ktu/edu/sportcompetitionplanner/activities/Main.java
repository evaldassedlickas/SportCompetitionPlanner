package ktu.edu.sportcompetitionplanner.activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ktu.edu.sportcompetitionplanner.R;

public class Main extends Base {
    private Button playersBtn, teamsBtn;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playersBtn = findViewById(R.id.btnPlayers);
        teamsBtn = findViewById(R.id.btnTeams);

        playersBtn.setOnClickListener(bntPlayersClick);
        teamsBtn.setOnClickListener(btnTeamsClick);

        subtitle = "Main";

    }

    View.OnClickListener bntPlayersClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ListPlayers.class);
            context.startActivity(intent);
        }
    };

    View.OnClickListener btnTeamsClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ListTeams.class);
            context.startActivity(intent);
        }
    };


}