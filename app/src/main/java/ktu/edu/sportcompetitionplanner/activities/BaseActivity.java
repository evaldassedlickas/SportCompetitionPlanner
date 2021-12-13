package ktu.edu.sportcompetitionplanner.activities;
import ktu.edu.sportcompetitionplanner.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    protected String subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(BaseActivity.this, LoginActivity.class));
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subtitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.upper_menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.profile:{
                showProfile();
                break;
            }
            case R.id.signOut:{
                Signout();
                break;
            }
            case R.id.main:{
                Main();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void Main(){
        startActivity(new Intent(BaseActivity.this, MainActivity.class));

    }


    private void Signout(){
        mAuth.signOut();
        finish();
        startActivity(new Intent(BaseActivity.this, LoginActivity.class));

    }

    private void showProfile(){
        startActivity(new Intent(BaseActivity.this, ProfilePreviewActivity.class));
    }



}