package ktu.edu.sportcompetitionplanner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ktu.edu.sportcompetitionplanner.R;
import ktu.edu.sportcompetitionplanner.models.Player;

public class CreatePlayerActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    DatabaseReference db;

    EditText firstnameEt, lastnameEt, positionEt, photoUrlEt;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        db = FirebaseDatabase.getInstance().getReference("Players");


        firstnameEt = findViewById(R.id.editTextFirstName);
        lastnameEt = findViewById(R.id.editTextLastName);
        positionEt = findViewById(R.id.editTextPosition);
        photoUrlEt = findViewById(R.id.editTextPhotoUrl);
        createBtn = findViewById(R.id.createPlayerBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createPlayer();
            }
        });


    }

    private void createPlayer() {
        String firstname = firstnameEt.getText().toString();
        String lastname = lastnameEt.getText().toString();
        String position = positionEt.getText().toString();
        String photoUrl = photoUrlEt.getText().toString();


        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(getApplicationContext(), "Please enter firstname...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            Toast.makeText(getApplicationContext(), "Please enter lastname!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(position)) {
            Toast.makeText(getApplicationContext(), "Please enter position!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(photoUrl)) {
            Toast.makeText(getApplicationContext(), "Please enter photoUrl!", Toast.LENGTH_LONG).show();
            return;
        }
        String playerID = db.push().getKey();
        Player player = new Player(firstname, lastname, position, photoUrl, playerID);

        db.child(playerID)
                .setValue(player);

        startActivity(new Intent(CreatePlayerActivity.this, PlayersListActivity.class));
        Toast.makeText(CreatePlayerActivity.this, "Player created sucussfully", Toast.LENGTH_LONG).show();

    }
}