package ip.histospot.android.activities.search;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.TreeMap;

import ip.histospot.android.R;
import ip.histospot.android.activities.SearchActivity;
import ip.histospot.android.activities.LoginActivity;
import ip.histospot.android.activities.VersusActivity;
import ip.histospot.android.activities.search.SearchRecyclerAdapter;
import ip.histospot.android.controllers.MainController;
import ip.histospot.android.model.SearchRow;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    private List<SearchRow> users;
    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rank;
        public ImageView picture;
        public TextView name;
        public Button follow;
        public TextView level;

        public ViewHolder(View view) {
            super(view);

            this.level = view.findViewById(R.id.level);
            this.rank = view.findViewById(R.id.rank);
            this.picture = view.findViewById(R.id.picture);
            this.name = view.findViewById(R.id.name);
            this.follow = view.findViewById(R.id.follow);
        }
    }

    public SearchRecyclerAdapter(List<SearchRow> moviesList, Activity activity) {
        this.users = moviesList;
        this.activity = activity;
    }

    public List<SearchRow> getUsers() {
        return users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchRow row = this.users.get(position);
        holder.rank.setText("" + (position + 1));
        holder.picture.setImageBitmap(row.getPicture());
        holder.name.setText(row.getName());
        holder.level.setText("" + row.getLevel());
        if (row.isFriend()) {
            holder.follow.setText("UNFOLLOW");
        } else {
            holder.follow.setText("FOLLOW");
        }
        holder.follow.setOnClickListener(l -> {
            int other = row.getId();
            int userId = this.activity.getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
            TreeMap<String, String> params = new TreeMap<>();
            params.put("follower", "" + userId);
            params.put("followed", "" + other);
            MainController.getInstance().follow(l.getContext(), params, ls -> {
                Toast.makeText(l.getContext(), "Done!", Toast.LENGTH_SHORT).show();
                SearchActivity sa = (SearchActivity) this.activity;
                sa.reset();
            }, ls -> {
                Toast.makeText(l.getContext(), "Error!", Toast.LENGTH_SHORT).show();
            });
        });
        holder.picture.setOnClickListener(l -> {
            Intent intent = new Intent(l.getContext(), VersusActivity.class);
            int other = row.getId();
            intent.putExtra(SearchActivity.OTHER_ID_EXTRA, other);
            int userId = this.activity.getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
            intent.putExtra(LoginActivity.USER_ID_EXTRA, userId);
            this.activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}