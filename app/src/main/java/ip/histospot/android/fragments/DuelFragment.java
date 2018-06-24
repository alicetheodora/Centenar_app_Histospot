package ip.histospot.android.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ip.histospot.android.R;
import ip.histospot.android.activities.DuelActivity;
import ip.histospot.android.activities.LeaderboardActivity;
import ip.histospot.android.activities.LoginActivity;
import ip.histospot.android.controllers.MainController;

/**
 * Created by alex on 31.03.2018.
 */

public class DuelFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    CircularProgressButton circularProgressButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_duel, container, false);
        this.circularProgressButton = rootView.findViewById(R.id.addDuel);
        this.circularProgressButton.setOnClickListener(l->{
            this.callDuel();
            this.circularProgressButton.startAnimation();
        });
        this.circularProgressButton.stopAnimation();

        ImageButton leaderboard = rootView.findViewById(R.id.leaderboard);
        leaderboard.setOnClickListener(l -> {
            Intent intent = new Intent(super.getContext(), LeaderboardActivity.class);
            int userId = getActivity().getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
            intent.putExtra(LoginActivity.USER_ID_EXTRA, userId);
            startActivity(intent);
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.circularProgressButton.stopAnimation();
    }

    private void callDuel() {
        int userId = getActivity().getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
        TreeMap<String, String> ob = new TreeMap<>();
        ob.put("id", "" + userId);
        MainController.getInstance().joinDuel(super.getContext(), ob, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Boolean ok = response.getBoolean("ready");

            if(!ok) {
                Log.d("DUEL", "Not ok!");
                new Thread(() -> {
                    try {
                        Log.d("DUEL", "Sleeping!");
                        Thread.sleep(3000);
                        Log.d("DUEL", "Slept!");
                        this.callDuel();
                        Log.d("DUEL", "Called!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

            } else {
                Intent intent = new Intent(super.getContext(), DuelActivity.class);
                int userId = getActivity().getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
                intent.putExtra(LoginActivity.USER_ID_EXTRA, userId);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
