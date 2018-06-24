package ip.histospot.android.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ip.histospot.android.R;
import ip.histospot.android.activities.leaderboard.LeaderboardRecyclerAdapter;
import ip.histospot.android.controllers.MainController;
import ip.histospot.android.model.LeaderboardRow;

/**
 * Created by alex on 07.04.2018.
 */

public class LeaderboardAllFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RecyclerView recyclerView;
    private LeaderboardRecyclerAdapter allAdapter;
    private List all = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leaderboard_all, container, false);

        this.recyclerView = rootView.findViewById(R.id.allList);
        this.allAdapter = new LeaderboardRecyclerAdapter(all);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(super.getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(this.allAdapter);

        MainController.getInstance().leaderboard(this.getContext(), "friends=false&page=0", this, this);

        return rootView;
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray users = (JSONArray) response.get("users");
            int length = users.length();
            List<LeaderboardRow> us = this.all;
            us.clear();
            for (int i = 0; i < length; ++i) {
                JSONObject user = (JSONObject) users.get(i);
                LeaderboardRow lr = new LeaderboardRow();
                lr.setId(user.getInt("id"));
                lr.setRank(user.getInt("rank"));
                lr.setName(user.getString("name"));
                lr.setLevel(user.getInt("level"));
                byte[] photoBytes = Base64.decode(user.getString("picture"), Base64.DEFAULT);
                Bitmap decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                if (decoded != null) {
                    lr.setPicture(decoded);
                }
                lr.setPoints(user.getInt("points"));
                us.add(lr);
            }
            this.allAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
