package com.istiabudi.cataloguemoviedb;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter{
    Context _context;
    public TabFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        _context = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new NowPlayingFragment();
                break;
            case 1:
                fragment = new UpcomingFragment();
                break;
            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String upcoming = _context.getResources().getString( R.string.upcoming );
        String nowplay = _context.getResources().getString( R.string.now_playing );

        String[] title = new String[]{
                nowplay, upcoming
        };
        return title[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
