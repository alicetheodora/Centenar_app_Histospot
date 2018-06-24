package ip.histospot.android.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import ip.histospot.android.R;
import ip.histospot.android.activities.search.SearchPagerAdapter;
import ip.histospot.android.activities.main.MainPagerAdapter;

public class SearchActivity extends AppCompatActivity {

    public static final String OTHER_ID_EXTRA = "otherId";

    private SearchPagerAdapter mPagerAdapter;
    private ViewPager mPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager());
        mPager = findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);

        TabLayout tabs = findViewById(R.id.tab_layout);
        tabs.setupWithViewPager(this.mPager);

        ImageButton searchButton = super.findViewById(R.id.search_button);
        searchButton.setOnClickListener(l -> {
            this.reset();
        });

    }

    public void reset() {
        int currentItem = mPager.getCurrentItem();
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(currentItem);
    }
}
