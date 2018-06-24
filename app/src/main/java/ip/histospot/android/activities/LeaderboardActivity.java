package ip.histospot.android.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ip.histospot.android.R;
import ip.histospot.android.activities.leaderboard.LeaderboardPagerAdapter;
import ip.histospot.android.activities.main.MainPagerAdapter;

public class LeaderboardActivity extends AppCompatActivity {

    public static final String OTHER_ID_EXTRA = "otherId";

    private LeaderboardPagerAdapter mPagerAdapter;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        mPagerAdapter = new LeaderboardPagerAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);

        TabLayout tabs = findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(this.mPager);
    }
}
