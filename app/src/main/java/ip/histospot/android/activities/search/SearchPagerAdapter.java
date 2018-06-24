package ip.histospot.android.activities.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ip.histospot.android.fragments.SearchAllFragment;
import ip.histospot.android.fragments.SearchFriendsFragment;

public class SearchPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLES = {"Prieteni", "Toti"};

    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new SearchFriendsFragment();
                break;
            case 1:
                fragment = new SearchAllFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return this.TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.TITLES[position];
    }
}

