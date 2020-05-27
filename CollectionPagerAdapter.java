package com.example.androidweek18;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {

    public CollectionPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);

    }

    @Override
    // Gets the the current page number and sent it to objectfragment who then shows it.
    public Fragment getItem(int i) {
        Fragment fragment = new objectFragment();
        Bundle args = new Bundle();
        args.putInt(objectFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    // How many pages are allowed
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return "OBJECT" + (position + 1);
    }
}
