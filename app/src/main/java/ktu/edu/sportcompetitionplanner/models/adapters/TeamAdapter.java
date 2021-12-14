package ktu.edu.sportcompetitionplanner.models.adapters;

        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.content.Context;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.ArrayList;

        import ktu.edu.sportcompetitionplanner.R;
        import ktu.edu.sportcompetitionplanner.activities.TeamPreview;
        import ktu.edu.sportcompetitionplanner.models.Team;


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Team> list;
    private DatabaseReference database;

    public TeamAdapter(Context mContext, ArrayList<Team> list) {
        this.mContext = mContext;
        this.list = list;
        database = FirebaseDatabase.getInstance().getReference("Teams");
    }



    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team team = list.get(position);

        holder.name.setText("Name: " + team.getname());
        holder.country.setText("Country: " + team.getcountry());
        holder.description.setText("Description: " + team.getdescription());

        holder.btnRemove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View itemView) {
                list.remove((holder.getAdapterPosition()));
                notifyItemRemoved(holder.getAdapterPosition());
                database.child(team.getuId()).removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, country,description;
        Button btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            country = itemView.findViewById(R.id.country);
            description = itemView.findViewById(R.id.description);
            btnRemove = itemView.findViewById(R.id.teamRemove);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View itemView) {
                    Team team = list.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, TeamPreview.class);
                    intent.putExtra("teamInfo", team);
                    mContext.startActivity(intent);
                }
            });



        }
    }


}
