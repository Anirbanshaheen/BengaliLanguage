package com.example.bengalilanguage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class BarisalActivity extends AppCompatActivity {
    private TabLayout tabLayoutBarisal;
    private ViewPager viewPagerBarisal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barisal);

        tabLayoutBarisal = findViewById(R.id.barisalTabLayout);
        viewPagerBarisal = findViewById(R.id.viewPagerBarisal);
//        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayoutBarisal.getTabCount());
        viewPagerBarisal.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),tabLayoutBarisal.getTabCount()));
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        String[] text = {"Relation", "Food", "Phrase"};

        public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new FragmentRelationBarisal();
            }
            if (position == 1) {
                return new FragmentFoodBarisal();
            }
            if (position == 2) {
                return new FragmentPhraseBarisal();
            }
            return null;
        }

        @Override
        public int getCount() {
            return text.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return text[position];
        }
    }
}
