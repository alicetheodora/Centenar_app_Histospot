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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ip.histospot.android.R;
import ip.histospot.android.activities.LoginActivity;
import ip.histospot.android.activities.search.SearchRecyclerAdapter;
import ip.histospot.android.controllers.MainController;
import ip.histospot.android.model.SearchRow;

public class SearchFriendsFragment  extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener  {

    private RecyclerView recyclerView;
    private SearchRecyclerAdapter friendsAdapter;
    private List friends = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_friends, container, false);

        this.recyclerView = rootView.findViewById(R.id.friendsList);
        this.friendsAdapter = new SearchRecyclerAdapter(friends, super.getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(super.getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(this.friendsAdapter);

        EditText queryText = super.getActivity().findViewById(R.id.search_text);
        String query = queryText.getText().toString();

        int userId = super.getActivity().getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
        MainController.getInstance().search(this.getContext(), "friends=true&page=0&id=" + userId + "&query=" + query, this, this);

        return rootView;
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray users = (JSONArray) response.get("users");
            int length = users.length();
            List<SearchRow> us = this.friends;
            us.clear();
            for (int i = 0; i < length; ++i) {
                JSONObject user = (JSONObject) users.get(i);
                SearchRow lr = new SearchRow();
                //lr.setRank(user.getInt("rank"));
                lr.setId(user.getInt("id"));
                lr.setName(user.getString("name"));
                lr.setLevel(user.getInt("level"));
                byte[] photoBytes = Base64.decode(user.getString("picture"), Base64.DEFAULT);
                Bitmap decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                if (decoded != null) {
                    lr.setPicture(decoded);
                }
                //lr.setPoints(user.getInt("points"));
                lr.setFriend(true);
                us.add(lr);
            }
            this.friendsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
