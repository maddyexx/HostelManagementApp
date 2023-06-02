package com.example.firstfirebaseproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragmentActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private String getTabTitle(int position) {
        switch (position) {
            case 0:
                return "Login";
            case 1:
                return "Signup";
            default:
                return "";
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_fragment);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager2);
        ViewPagerAdaptor adaptor =new ViewPagerAdaptor(this);
        String [] titles = {"Login", "Signup"};
        viewPager.setAdapter(adaptor);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {tab.setText(getTabTitle(position));}
        ).attach();
    }
}