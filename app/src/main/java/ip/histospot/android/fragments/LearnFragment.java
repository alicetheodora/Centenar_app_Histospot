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
import ip.histospot.android.activities.learn.LearnRecyclerAdapter;
import ip.histospot.android.controllers.MainController;
import ip.histospot.android.model.LearnRow;

/**
 * Created by Denisa on 04.05.2018.
 */

public class LearnFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private RecyclerView recyclerView;
    private LearnRecyclerAdapter learnAdapter;
    private List all = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);

        this.recyclerView = rootView.findViewById(R.id.learnList);
        this.learnAdapter = new LearnRecyclerAdapter(all);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(super.getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(this.learnAdapter);

        MainController.getInstance().learn(this.getContext(), "", this, this);

        return rootView;
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray users = (JSONArray) response.get("titles");
            int length = users.length();
            List<LearnRow> us = this.all;
            us.clear();
            for (int i = 0; i < length; ++i) {
                JSONObject user = (JSONObject) users.get(i);
                LearnRow lr = new LearnRow();
                lr.setId(user.getInt("id"));
                lr.setName(user.getString("title"));
                us.add(lr);
            }
            this.learnAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
