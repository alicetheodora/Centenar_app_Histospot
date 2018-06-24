package ip.histospot.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;

import ip.histospot.android.R;
import ip.histospot.android.activities.LeaderboardActivity;
import ip.histospot.android.activities.SearchActivity;
import ip.histospot.android.activities.LoginActivity;
import ip.histospot.android.controllers.MainController;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ImageView pozaProfil;
    private static final int REQUEST_PHOTO_CODE = 100;
    private static final int MAX_IMAGE_DIMENSION = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        pozaProfil = rootView.findViewById(R.id.poza_profil);

        pozaProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Schimbare poza profil"), REQUEST_PHOTO_CODE);
            }
        });

        this.callProfile();


        ImageButton search = rootView.findViewById(R.id.search);
        search.setOnClickListener(l -> {
            Intent intent = new Intent(super.getContext(), SearchActivity.class);
            int userId = getActivity().getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
            intent.putExtra(LoginActivity.USER_ID_EXTRA, userId);
            startActivity(intent);
        });

        ImageButton retry = rootView.findViewById(R.id.retry);
        retry.setOnClickListener(l -> {
            this.callProfile();
        });

        return rootView;
    }

    private void callProfile() {
        int userId = getActivity().getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
        String query = "id=" + userId;
        MainController.getInstance().profile(super.getContext(), query, this, this);
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
            float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
            float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        return srcBitmap;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_CODE && resultCode == RESULT_OK) {
            try {
                Uri selectedImageUri = data.getData();
                Bitmap bm = getCorrectlyOrientedImage(this.getContext(), selectedImageUri);

                int w = bm.getWidth();
                int h = bm.getHeight();
                int t = Math.min(w, h);
                int min = Math.min(t, 200);
                if (t >= min) {
                    if (w > h) {
                        float scale = (float) min / (float) w;
                        w = min;
                        h *= scale;
                    } else {
                        float scale = (float) min / (float) w;
                        h = min;
                        w *= scale;
                    }

                    bm = Bitmap.createScaledBitmap(bm, w, h, false);
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

                int userId = getActivity().getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
                TreeMap<String, String> params = new TreeMap<>();
                params.put("id", "" + userId);
                params.put("photo", encodedImage);
                MainController.getInstance().updateProfile(super.getContext(), params, response -> {
                    this.callProfile();
                }, error -> {
                    Log.e("Profile update api", error.getMessage());
                    Toast.makeText(super.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(super.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            TextView nume = super.getView().findViewById(R.id.nume_utilizator);
            nume.setText(response.getString("firstName"));

            TextView prenume = super.getView().findViewById(R.id.prenume_utilizator);
            prenume.setText(response.getString("lastName"));
            if (response.has("photo")) {
                byte[] photoBytes = Base64.decode(response.getString("photo"), Base64.DEFAULT);

                ImageView image = this.getView().findViewById(R.id.poza_profil);
                Bitmap decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                image.setImageBitmap(decoded);
            }

            TextView nivelText = super.getView().findViewById(R.id.level);
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
            bar.setProgress((int) Math.round(progress));
        } catch (JSONException e) {
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        RelativeLayout layout = super.getView().findViewById(R.id.no_internet_layout);
        layout.setVisibility(RelativeLayout.VISIBLE);
    }
}
