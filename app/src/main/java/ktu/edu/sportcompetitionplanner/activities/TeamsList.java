package ktu.edu.sportcompetitionplanner.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ktu.edu.sportcompetitionplanner.R;
import ktu.edu.sportcompetitionplanner.models.Team;
import ktu.edu.sportcompetitionplanner.models.adapters.TeamAdapter;


public class TeamsList extends Base {
    TextView noTeamRecordsMessage;

    private FloatingActionButton createBtn;
    private RecyclerView recyclerView;
    private DatabaseReference teamsRef;
    private ArrayList<Team> teams;
    private TeamAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_list);
        subtitle = "Teams list";
        createBtn = findViewById(R.id.createBtn);
        noTeamRecordsMessage = findViewById(R.id.noTeamRecordsTv);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.TeamsrecyclerView);
        teamsRef = FirebaseDatabase.getInstance().getReference("Teams");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        teams = new ArrayList<>();

        adapter = new TeamAdapter(this, teams);
        recyclerView.setAdapter(adapter);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeamsList.this, "Add team", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TeamsList.this, CreateTeam.class));
            }
        });

        teamsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teams.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Team team = dataSnapshot.getValue(Team.class);
                    teams.add(team);

                }
                if(teams == null || teams.size() == 0)
                {
                    noTeamRecordsMessage.setVisibility(View.VISIBLE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}