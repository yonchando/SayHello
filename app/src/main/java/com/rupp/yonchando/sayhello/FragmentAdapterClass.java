package com.rupp.yonchando.sayhello;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by PC User on 14-Jan-18.
 */

class FragmentAdapterClass extends FragmentPagerAdapter {


    public FragmentAdapterClass(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 1:
                FriendFragment friendFragment = new FriendFragment();
                return friendFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
