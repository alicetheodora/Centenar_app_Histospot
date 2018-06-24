package ip.histospot.android.controllers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

import ip.histospot.android.activities.VersusActivity;
import ip.histospot.android.fragments.ProfileFragment;
import ip.histospot.android.utilities.ApiRequest;

public final class MainController {

    public static String SERVER_ADRESS = "http://192.168.43.219:8080/Histospot/";
    public static String API_ADRESS = MainController.SERVER_ADRESS + "api/";

    private static final MainController instance = new MainController();

    private MainController() {
    }

    public static MainController getInstance() {
        return instance;
    }

    public void login(Context context, Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.POST, MainController.API_ADRESS + "login", params, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void profile(Context context, String query, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.GET, MainController.API_ADRESS + "profile?" + query, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void updateProfile(Context context, TreeMap<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.POST, MainController.API_ADRESS + "updateProfile", params, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void leaderboard(Context context, String query, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.GET, MainController.API_ADRESS + "leaderboard?" + query, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void learn(Context context, String query, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.GET, MainController.API_ADRESS + "learn?" + query, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void search(Context context, String query, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.GET, MainController.API_ADRESS + "search?" + query, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void joinDuel(Context context, TreeMap<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.POST, MainController.API_ADRESS + "joinDuel", params, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void vs(Context context, String query, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.GET, MainController.API_ADRESS + "vs?" + query, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void follow(Context context, TreeMap<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.POST, MainController.API_ADRESS + "follow", params, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void duel (Context context, String query, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.GET, MainController.API_ADRESS + "duel?" + query, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void answerDuel(Context context, TreeMap<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.POST, MainController.API_ADRESS + "answerDuel", params, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }
}

