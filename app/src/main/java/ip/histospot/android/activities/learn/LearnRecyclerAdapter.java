package ip.histospot.android.activities.learn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ip.histospot.android.R;
import ip.histospot.android.activities.LearnActivity;
import ip.histospot.android.activities.LoginActivity;
import ip.histospot.android.activities.VersusActivity;
import ip.histospot.android.model.LearnRow;

/**
 * Created by Denisa on 04.05.2018.
 */

public class LearnRecyclerAdapter extends RecyclerView.Adapter<LearnRecyclerAdapter.ViewHolder> {

    private List<LearnRow> users;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View view) {
            super(view);

            this.name = view.findViewById(R.id.name);
        }
    }


    public LearnRecyclerAdapter(List<LearnRow> moviesList) {
        this.users = moviesList;
    }

    public List<LearnRow> getUsers() {
        return users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LearnRow row = this.users.get(position);
        holder.name.setText(row.getName());

            holder.name.setOnClickListener(l -> {
            Intent intent = new Intent(l.getContext(), LearnActivity.class);
            intent.putExtra(LearnActivity.LESSON_EXTRA, this.users.get(position).getId());
            Activity a = (Activity) l.getContext();
            a.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}