package ip.histospot.android.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import ip.histospot.android.R;
import ip.histospot.android.activities.main.MainPagerAdapter;

public class MainActivity extends FragmentActivity {

    public static final String TAB_EXTRA ="tabExtra";

    private MainPagerAdapter mPagerAdapter;
    private ViewPager mPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);

        TabLayout tabs = findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(this.mPager);

        tabs.getTabAt(0).setIcon(R.drawable.ic_local_library_white_36dp);
        tabs.getTabAt(1).setIcon(R.drawable.ic_people_outline_white_36dp);
        tabs.getTabAt(2).setIcon(R.drawable.ic_account_circle_white_36dp);

        int tab = getIntent().getIntExtra(MainActivity.TAB_EXTRA, 2);

        this.mPager.setCurrentItem(tab);
    }
}
