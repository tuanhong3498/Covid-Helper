package com.example.covidhelper.ui.announcement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.example.covidhelper.ui.profile.ProfileFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementFragment extends Fragment
{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private AnnouncementAllFragment announcementAllFragment;
    private AnnouncementTaskFragment announcementTaskFragment;
    private AnnouncementInformationFragment announcementInformationFragment;
    private AnnouncementFakeNewsFragment announcementFakeNewsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_announcement, container, false);

        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tab_layout);

        announcementAllFragment = new AnnouncementAllFragment();
        announcementTaskFragment = new AnnouncementTaskFragment();
        announcementInformationFragment = new AnnouncementInformationFragment();
        announcementFakeNewsFragment = new AnnouncementFakeNewsFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(announcementAllFragment,"All");
        viewPagerAdapter.addFragment(announcementTaskFragment,"Task");
        viewPagerAdapter.addFragment(announcementInformationFragment,"Information");
        viewPagerAdapter.addFragment(announcementFakeNewsFragment,"Fake news");
        viewPager.setAdapter(viewPagerAdapter);

        return root;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> tabTitle = new ArrayList<>();
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