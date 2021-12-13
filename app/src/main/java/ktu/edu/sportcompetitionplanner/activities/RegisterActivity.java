package ktu.edu.sportcompetitionplanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ktu.edu.sportcompetitionplanner.R;
import ktu.edu.sportcompetitionplanner.models.User;

public class RegisterActivity extends AppCompatActivity {
    EditText inputEmail, inputPassword;
    FirebaseAuth mauth;
    DatabaseReference usersRef;
    private FirebaseDatabase mDatabse;
    private Button registerBtn;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUI();
        mauth = FirebaseAuth.getInstance();
        mDatabse = FirebaseDatabase.getInstance();
        usersRef = mDatabse.getReference("Users");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Registration");

    }


    private void initializeUI() {
        inputEmail = findViewById(R.id.editTextEmail);
        inputPassword = findViewById(R.id.editTextPassword);
        registerBtn = findViewById(R.id.btnRegister);
       registerBtn.setOnClickListener(registerClick);
    }

    View.OnClickListener registerClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            register();

        }
    };

    private void register() {
        String  email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }


        mauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User vartotojas = new User();
                            vartotojas.setEmail((email));
                            vartotojas.setUid((mauth.getCurrentUser().getUid()));
                            usersRef.child(vartotojas.getUid())
                                    .setValue((vartotojas));



                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "Registration completed", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}