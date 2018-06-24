package ip.histospot.android.activities.leaderboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ip.histospot.android.fragments.DuelFragment;
import ip.histospot.android.fragments.LeaderboardAllFragment;
import ip.histospot.android.fragments.LeaderboardFriendsFragment;
import ip.histospot.android.fragments.LearnFragment;
import ip.histospot.android.fragments.ProfileFragment;

/**
 * Created by alex on 03.04.2018.
 */

public class LeaderboardPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLES = {"Prieteni", "Toti"};

    public LeaderboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new LeaderboardFriendsFragment();
                break;
            case 1:
                fragment = new LeaderboardAllFragment();
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
