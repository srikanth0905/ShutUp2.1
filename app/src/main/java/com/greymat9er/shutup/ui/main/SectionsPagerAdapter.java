package com.greymat9er.shutup.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.firebase.auth.FirebaseUser;
import com.greymat9er.shutup.ChatFragment;
import com.greymat9er.shutup.FriendsFragment;
import com.greymat9er.shutup.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private String mUserName;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String mUserName) {
        super(fm);
        mContext = context;
        this.mUserName = mUserName;
        Log.i("SectionsPagerAdapter: ", mUserName);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                return new ChatFragment(mUserName);
            case 1:
                return new FriendsFragment();
            default:
                return PlaceholderFragment.newInstance(position + 1);
        }
//        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        //return number of tabs
        return TAB_TITLES.length;
    }
}