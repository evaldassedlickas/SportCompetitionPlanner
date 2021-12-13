package ktu.edu.sportcompetitionplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ktu.edu.sportcompetitionplanner.R;
import ktu.edu.sportcompetitionplanner.models.Team;


public class CreateTeam extends Base {
    DatabaseReference db;

    EditText nameEt, countryEt, descriptionEt;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        subtitle = "Team create";



        db = FirebaseDatabase.getInstance().getReference("Teams");


        nameEt = findViewById(R.id.etName);
        countryEt = findViewById(R.id.etCountry);
        descriptionEt = findViewById(R.id.etDescription);
        createBtn = findViewById(R.id.createBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTeam();
            }
        });

    }

    private void createTeam() {
        String name = nameEt.getText().toString();
        String country = countryEt.getText().toString();
        String description = descriptionEt.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please enter team name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(country)) {
            Toast.makeText(getApplicationContext(), "Please enter country!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "Please enter description!", Toast.LENGTH_LONG).show();
            return;
        }
        String teamID = db.push().getKey();
        Team team = new Team(name, country, description, teamID);

        db.child(teamID)
                .setValue(team);

        startActivity(new Intent(CreateTeam.this, TeamsList.class));
        Toast.makeText(CreateTeam.this, "Team created sucussfully", Toast.LENGTH_LONG).show();

    }
}