package ip.histospot.android.activities.leaderboard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ip.histospot.android.R;
import ip.histospot.android.activities.LeaderboardActivity;
import ip.histospot.android.activities.LoginActivity;
import ip.histospot.android.activities.VersusActivity;
import ip.histospot.android.model.LeaderboardRow;

/**
 * Created by alex on 06.04.2018.
 */

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.ViewHolder> {

    private List<LeaderboardRow> users;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rank;
        public ImageView picture;
        public TextView name;
        public TextView points;
        public TextView level;

        public ViewHolder(View view) {
            super(view);

            this.level = view.findViewById(R.id.level);
            this.rank = view.findViewById(R.id.rank);
            this.picture = view.findViewById(R.id.picture);
            this.name = view.findViewById(R.id.name);
            this.points = view.findViewById(R.id.points);
        }
    }


    public LeaderboardRecyclerAdapter(List<LeaderboardRow> moviesList) {
        this.users = moviesList;
    }

    public List<LeaderboardRow> getUsers() {
        return users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LeaderboardRow row = this.users.get(position);
        holder.rank.setText("" + row.getRank());
        holder.picture.setImageBitmap(row.getPicture());
        holder.name.setText(row.getName());
        holder.level.setText("" + row.getLevel());
        holder.points.setText(row.getPoints() + " p");
        holder.picture.setOnClickListener(l -> {
            Intent intent = new Intent(l.getContext(), VersusActivity.class);
            int other = row.getId();
            intent.putExtra(LeaderboardActivity.OTHER_ID_EXTRA, other);
            Activity acc = (Activity) l.getContext();
            int userId = acc.getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
            intent.putExtra(LoginActivity.USER_ID_EXTRA, userId);
            acc.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}