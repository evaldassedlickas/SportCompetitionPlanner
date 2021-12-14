package ktu.edu.sportcompetitionplanner.activities;
import ktu.edu.sportcompetitionplanner.R;
import  ktu.edu.sportcompetitionplanner.models.Player;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PlayerPreview extends Base {

    private TextView tvFirstname, tvLastname, tvPosition;
    private ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);
         subtitle = "Player details";
        tvFirstname = findViewById(R.id.tvFirstname);
        tvLastname = findViewById(R.id.tvLastname);
        tvPosition = findViewById(R.id.tvPosition);
        ivPhoto = findViewById(R.id.ivPhoto);


        Intent intent = getIntent();

        Player player = intent.getParcelableExtra("playerInfo");

        tvFirstname.setText(player.getfirstname());
        tvLastname.setText(player.getlastname());
        tvPosition.setText(player.getposition());

        Glide.with(this)
                .load(player.getphotoUrl())
                .into(ivPhoto);

    }
}