package com.example.covidhelper.ui.announcement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.covidhelper.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_announcement, container, false);

        ViewPager viewPager = root.findViewById(R.id.view_pager);
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);

        AnnouncementAllFragment announcementAllFragment = new AnnouncementAllFragment();
        AnnouncementTaskFragment announcementTaskFragment = new AnnouncementTaskFragment();
        AnnouncementInformationFragment announcementInformationFragment = new AnnouncementInformationFragment();
        AnnouncementFakeNewsFragment announcementFakeNewsFragment = new AnnouncementFakeNewsFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(announcementAllFragment,"All");
        viewPagerAdapter.addFragment(announcementTaskFragment,"Task");
        viewPagerAdapter.addFragment(announcementInformationFragment,"Information");
        viewPagerAdapter.addFragment(announcementFakeNewsFragment,"Fake news");
        viewPager.setAdapter(viewPagerAdapter);

        return root;
    }

    private static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> tabTitle = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            tabTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return tabTitle.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle.get(position);
        }
    }
}