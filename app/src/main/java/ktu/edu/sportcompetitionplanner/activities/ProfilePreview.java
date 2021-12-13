package ktu.edu.sportcompetitionplanner.activities;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ktu.edu.sportcompetitionplanner.R;
import ktu.edu.sportcompetitionplanner.models.User;

public class ProfilePreview extends Base {
    private FirebaseAuth mauth;
    private FirebaseDatabase database;
    private TextView tvEmail, tvUserUid;
    private ImageView profilePic;

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mauth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(ProfilePreview.this, LoginActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_preview);
        initiliazeUI();
        mauth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String id = mauth.getCurrentUser().getUid();

        DatabaseReference databaseReference = database.getReference()
                .child("Users/" + id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                tvEmail.setText("E-mail: " + userProfile.getEmail());
               // tvUserUid.setText("userID: " + userProfile.getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfilePreview.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Profile information");
    }

    private void initiliazeUI(){
        tvEmail= findViewById(R.id.tvEmail);
      //  tvUserUid = findViewById(R.id.tvUserUid);
       // profilePic = findViewById(R.id.profilePic);

    }



}