package com.example.android.lapitchat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                ChatsFragment mChatsFragment = new ChatsFragment();
                return mChatsFragment;

            case 1:
                FreindsFragment mFreindsFragment = new FreindsFragment();
                return mFreindsFragment;

            case 2:
                RequestsFragment mRequestsFragment = new RequestsFragment();
                return mRequestsFragment;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;  //Three Fragments
    }
    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return  "CHATS";
            case 1:
                return "FREINDS";
            case 2:
                return "REQUESTS";
        }
        return null;
    }
}
