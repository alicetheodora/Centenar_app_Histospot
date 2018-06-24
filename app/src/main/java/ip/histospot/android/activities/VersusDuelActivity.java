package ip.histospot.android.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import ip.histospot.android.R;
import ip.histospot.android.controllers.MainController;

public class VersusDuelActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versus_duel);
        this.callApi();

        ImageButton retry = findViewById(R.id.retry);
        retry.setOnClickListener(l -> {
            this.callApi();
        });
    }

    private void callApi() {
        int youId = this.getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
        int otherId = this.getIntent().getIntExtra(LeaderboardActivity.OTHER_ID_EXTRA, 0);
        String query = "you=" + youId + "&other=" + otherId;
        MainController.getInstance().vs(this, query, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject pr = response.getJSONObject("you");
            TextView nume = findViewById(R.id.youName);
            nume.setText(pr.getString("name"));

            if (response.has("photo")) {
                byte[] photoBytes = Base64.decode(response.getString("photo"), Base64.DEFAULT);

                ImageView image = this.findViewById(R.id.youImage);
                Bitmap decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                image.setImageBitmap(decoded);
            }

            pr = response.getJSONObject("other");
            nume = findViewById(R.id.otherName);
            nume.setText(pr.getString("name"));

            if (response.has("photo")) {
                byte[] photoBytes = Base64.decode(response.getString("photo"), Base64.DEFAULT);

                ImageView image = this.findViewById(R.id.otherImage);
                Bitmap decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                image.setImageBitmap(decoded);
            }

            /*TextView nivelText = super.findViewById(R.id.youLevel);
            int nivel = response.getInt("level");
            nivelText.setText("" + nivel);

            int scorCurent = response.getInt("exp");
            int scorStart = response.getInt("startExp");
            int scorNext = response.getInt("endExp");

            TextView progressText = super.getView().findViewById(R.id.progress_text);
            progressText.setText(scorCurent + "/" + scorNext + " puncte");

            scorCurent -= scorStart;
            scorNext -= scorStart;

            double progress = scorCurent * 100.0 / scorNext;
            ProgressBar bar = super.getView().findViewById(R.id.level_bar);
            bar.setProgress((int) Math.round(progress));*/
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        RelativeLayout layout = super.findViewById(R.id.no_internet_layout);
        layout.setVisibility(RelativeLayout.VISIBLE);
    }
}
