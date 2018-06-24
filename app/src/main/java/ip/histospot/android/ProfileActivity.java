package ip.histospot.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.profile, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem it) {
        int id = it.getItemId();

        if(id == R.id.history) {
            Intent i = new Intent(ProfileActivity.this, HistoryActivity.class);
            startActivity(i);
        }

        if(id == R.id.lessons) {
            startActivity(new Intent(ProfileActivity.this, LessonsActivity.class));
        }

        if(id == R.id.players) {
            startActivity(new Intent(ProfileActivity.this, PlayersActivity.class));
        }

        if(id == R.id.game) {
            startActivity(new Intent(ProfileActivity.this, GameActivity.class));
        }

        return true;
    }
}


