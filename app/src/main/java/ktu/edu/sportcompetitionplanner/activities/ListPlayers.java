package ktu.edu.sportcompetitionplanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ktu.edu.sportcompetitionplanner.R;
import ktu.edu.sportcompetitionplanner.models.Player;
import ktu.edu.sportcompetitionplanner.models.adapters.PlayersAdapter;

public class ListPlayers extends AppCompatActivity {
    TextView noPlayerRecordsMessage;
    private RecyclerView recyclerView;
    private DatabaseReference playersRef;
    private FirebaseAuth mAuth;
    private ArrayList<Player> playersList;
    private PlayersAdapter playersAdapter;
    private FloatingActionButton createBtn;


    SharedPreferences sharedPreferences;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);
        noPlayerRecordsMessage = findViewById(R.id.noPlayersRecordsTv);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences= getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = sharedPreferences.getString("Sort", "newest");

        if(mSorting.equals("newest")){
            linearLayoutManager =new LinearLayoutManager(this);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
        }
        else if (mSorting.equals("oldest")){
            linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setReverseLayout(false);
            linearLayoutManager.setStackFromEnd(false);
        }


        recyclerView = findViewById(R.id.recyclerView);
        playersRef = FirebaseDatabase.getInstance().getReference("Players");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        playersList = new ArrayList<>();

        playersAdapter = new PlayersAdapter(this, playersList);
        recyclerView.setAdapter(playersAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("All players");

        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListPlayers.this, "Add player", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ListPlayers.this, CreatePlayer.class));
            }
        });

        //Clear();
        //GetData2();
        playersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                playersList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Player player = dataSnapshot.getValue(Player.class);
                    playersList.add(player);
                    //  String firstname = dataSnapshot.getValue("dd") .toString();
                    //String lastname = snapshot.child("lastname").getValue().toString();
                    //  player.(snapshot.child("firstname").getValue().toString());
                    // player.setLastname(snapshot.child("lastname").getValue().toString());

                }
                if(playersList == null || playersList.size() == 0)
                {
                    noPlayerRecordsMessage.setVisibility(View.VISIBLE);
                }
                playersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    private void GetData(){
        Query query = playersRef.child("Players");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Clear();
                DataSnapshot info = dataSnapshot;

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Player player = dataSnapshot.getValue(Player.class);

                    // player.setFirstname(dataSnapshot.child("firstname").getValue().toString());

                    //player.setLastname(dataSnapshot.child("lastname").getValue().toString());
                    // player.setPosition(dataSnapshot.child("position").getValue().toString());
                    // player.setPhotoUrl(dataSnapshot.child("photoUrl").getValue().toString());

                    playersList.add(player);
                }
                playersAdapter = new PlayersAdapter(getApplicationContext(), playersList);
                recyclerView.setAdapter(playersAdapter);
                playersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Clear(){
        if(playersList != null){
            playersList.clear();
            if(playersAdapter != null){
                playersAdapter.notifyDataSetChanged();
            }
        }
        playersList = new ArrayList<>();

    }


    private void GetData2(){

        playersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    //Player player = new Player();
                    //  player.setFirstname(snapshot.child("firstname").getValue().toString());
                    //playersList.add(player);
                    Player player =  new Player();

                    // player.setFirstname(snapshot.child("firstname").getValue().toString());
                    //player.setFirstname(snapshot.child("lastname").getValue().toString());
                    // player.setPhotoUrl(snapshot.child("photoUrl").getValue().toString());

                    playersList.add(player);
                }

                playersAdapter.notifyDataSetChanged();

                //  playersAdapter = new PlayersAdapter(getApplicationContext(), playersList);
                // recyclerView.setAdapter(playersAdapter);
                // playersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Toast.makeText(PlayersListActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.upper_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //notesAdapter.getFilter().filter(newText);
                search(newText);

                return true;
            }
        });
        return true;
    }

    private void search(String str) {
        ArrayList<Player> filtered = new ArrayList<>();
        for(Player object : playersList)
        {
            if(object.getfirstname().toLowerCase().startsWith(str.toLowerCase()))
            {
                filtered.add(object);
            }
        }
        PlayersAdapter filteredAdapter =new PlayersAdapter(ListPlayers.this, filtered);
        recyclerView.setAdapter(filteredAdapter);
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
            case R.id.sortMenu:{
                showSortDialog();
                break;
            }
            case R.id.main:{
                Main();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(ListPlayers.this, Login.class));
        }
    }

    private void Signout(){
        mAuth.signOut();
        finish();
        startActivity(new Intent(ListPlayers.this, Login.class));

    }

    private void showProfile(){
        startActivity(new Intent(ListPlayers.this, ProfilePreview.class));
    }

    private void Main(){
        startActivity(new Intent(ListPlayers.this, Main.class));

    }

    private void showSortDialog() {
        String[] sortOption = {"Ascending", "Descending"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                // .setIcon(R.drawable.ic_action_name)
                .setItems(sortOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which ==0){

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Sort", "newest");
                            editor.apply();
                            recreate();

                        }else if(which==1){
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Sort", "oldest");
                                editor.apply();
                                recreate();

                            }
                        }
                    }
                });
        builder.show();
    }


}