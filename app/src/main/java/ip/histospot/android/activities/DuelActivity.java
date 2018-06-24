package ip.histospot.android.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

import ip.histospot.android.R;
import ip.histospot.android.controllers.MainController;

public class DuelActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ImageView imagine_intrebare;
    private TextView intrebare;

    private Button.OnClickListener answerListener = (b) -> {
        final AnswerButton ab = (AnswerButton) b;
        int userId = this.getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);

        TreeMap<String, String> params = new TreeMap<>();
        params.put("answer", "" + ab.getIndex());
        params.put("id", "" + userId);
        MainController.getInstance().answerDuel(this, params, r -> {
            try {
                int ans = r.getInt("answer");

                LinearLayout ll = super.findViewById(R.id.buttons);
                int count = ll.getChildCount();
                for (int i = 0; i < count; ++i) {
                    AnswerButton t = (AnswerButton) ll.getChildAt(i);
                    if (t.getIndex() == ans) {
                        t.setBackgroundColor(Color.parseColor("#226800"));
                        break;
                    }
                }

                if(ans != ab.getIndex()){
                    ab.setBackgroundColor(Color.parseColor("#C70102"));
                }

                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        this.callApi();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, this);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel);


        intrebare = findViewById(R.id.intrebare);
        imagine_intrebare = findViewById(R.id.imagine_intrebare);

        callApi(true);
    }


    private void callApi() {
        this.callApi(false);
    }

    private void callApi(boolean data) {
        int userId= this.getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
        String query = "id=" + userId + "&question=true&time=true";
        if (data) {
            query += "&data=true";
        }
        MainController.getInstance().duel(this, query, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response.has("done") && response.getBoolean("done")) {
                 finish();
                return;
            }

            intrebare.setText(response.getString("question"));

            if (response.has("image")) {
                byte[] photoBytes = Base64.decode(response.getString("image"), Base64.DEFAULT);

                Bitmap decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                imagine_intrebare.setImageBitmap(decoded);

                imagine_intrebare.setVisibility(View.VISIBLE);
            } else {
                imagine_intrebare.setVisibility(View.GONE);
            }

            if (response.has("youImg") && response.has("otherImg")) {
                ImageView imageView = super.findViewById(R.id.pictureYou);
                byte[] photoBytes = Base64.decode(response.getString("youImg"), Base64.DEFAULT);
                Bitmap decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                imageView.setImageBitmap(decoded);

                imageView = super.findViewById(R.id.pictureOther);
                photoBytes = Base64.decode(response.getString("otherImg"), Base64.DEFAULT);
                decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                imageView.setImageBitmap(decoded);

                TextView textView = super.findViewById(R.id.youName);
                String name = response.getString("youName");
                textView.setText(name);

                textView = super.findViewById(R.id.otherName);
                name = response.getString("otherName");
                textView.setText(name);
            }

            ArrayList<AnswerButton> buttons = new ArrayList<>();
            JSONArray answers = response.getJSONArray("answers");
            int length = answers.length();
            for (int i = 0; i < length; ++i) {
                AnswerButton b = new AnswerButton(this, i);
                b.setText(answers.getString(i));
                b.setOnClickListener(this.answerListener);
                b.setBackgroundResource(R.color.colorSecondary);
                b.setTextColor(0xff000000);
                buttons.add(b);
            }

            Collections.shuffle(buttons);

            LinearLayout ll = super.findViewById(R.id.buttons);
            ll.removeAllViews();
            for (AnswerButton b : buttons) {
                ll.addView(b);
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        /*RelativeLayout layout = super.findViewById(R.id.no_internet_layout);
        layout.setVisibility(RelativeLayout.VISIBLE);*/
    }



    private class AnswerButton extends android.support.v7.widget.AppCompatButton {

        protected int index;

        public AnswerButton(Context context) {
            super(context);
        }

        public AnswerButton(Context context, int index) {
            super(context);
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
