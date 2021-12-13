package ktu.edu.sportcompetitionplanner.models.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ktu.edu.sportcompetitionplanner.R;
import ktu.edu.sportcompetitionplanner.activities.PlayerDetails;
import ktu.edu.sportcompetitionplanner.models.Player;


public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Player> playersList;
    private DatabaseReference database;

    public PlayersAdapter(Context mContext, ArrayList<Player> playersList) {
        this.mContext = mContext;
        this.playersList = playersList;
        database = FirebaseDatabase.getInstance().getReference("Players");
    }



    @NonNull
    @Override
    public PlayersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player player = playersList.get(position);
        holder.playerFullname.setText("Firstname, lastname: " + player.getfirstname() + " " + player.getlastname());
        holder.position.setText("Position " + player.getposition());

        Glide.with(mContext)
               .load(playersList.get(position).getphotoUrl())
                .into(holder.imageView);

        holder.playerRemove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View itemView) {
                playersList.remove((holder.getAdapterPosition()));
                notifyItemRemoved(holder.getAdapterPosition());
                database.child(player.getuId()).removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView, playerRemove;
        TextView playerFullname, position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.playerPicture);
            playerFullname = itemView.findViewById(R.id.playerFullName);
            position = itemView.findViewById(R.id.position);
            playerRemove = itemView.findViewById(R.id.playerRemove);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View itemView) {
                    Player player = playersList.get(getAdapterPosition());
                   // int position = ViewHolder.super.getAdapterPosition();
                    Intent intent = new Intent(mContext, PlayerDetails.class);
                    intent.putExtra("playerInfo", player);
                    mContext.startActivity(intent);

                }
            });



        }
    }


}
