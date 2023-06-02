package com.example.firstfirebaseproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdaptor extends FragmentStateAdapter {
    public ViewPagerAdaptor(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new LoginFragment();
        }
        else{
            return new SignUpFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
