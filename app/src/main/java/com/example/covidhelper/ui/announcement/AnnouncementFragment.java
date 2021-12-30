package com.example.covidhelper.ui.announcement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.ui.Sign.LoginActivity;
import com.example.covidhelper.ui.Sign.SignUpActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnnouncementFragment extends Fragment
{
    AnnouncementViewModel announcementViewModel;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_announcement, container, false);

        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        announcementViewModel = factory.create(AnnouncementViewModel.class);
        sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

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
        viewPagerAdapter.addFragment(announcementInformationFragment,"Inform");
        viewPagerAdapter.addFragment(announcementFakeNewsFragment,"Fake news");
        viewPager.setAdapter(viewPagerAdapter);

        BadgeDrawable badgeDrawableAll, badgeDrawableTask, badgeDrawableInformation, badgeDrawableFakeNews;
        badgeDrawableAll = Objects.requireNonNull(tabLayout.getTabAt(0)).getOrCreateBadge();
        badgeDrawableTask = Objects.requireNonNull(tabLayout.getTabAt(1)).getOrCreateBadge();
        badgeDrawableInformation = Objects.requireNonNull(tabLayout.getTabAt(2)).getOrCreateBadge();
        badgeDrawableFakeNews = Objects.requireNonNull(tabLayout.getTabAt(3)).getOrCreateBadge();
        badgeDrawableAll.setVisible(true);
        badgeDrawableTask.setVisible(true);
        badgeDrawableInformation.setVisible(true);
        badgeDrawableFakeNews.setVisible(true);

        announcementViewModel.getAnnouncementNumberInAll(sp.getInt("userID", -1)).observe(getActivity(), numberUnread -> {
            badgeDrawableAll.setNumber(numberUnread);
            setBadgeDrawableVisible (numberUnread, badgeDrawableAll);
        });
        announcementViewModel.getAnnouncementNumber(sp.getInt("userID", -1), "task").observe(getActivity(), numberUnread -> {
            badgeDrawableTask.setNumber(numberUnread);
            setBadgeDrawableVisible (numberUnread, badgeDrawableTask);
        });
        announcementViewModel.getAnnouncementNumber(sp.getInt("userID", -1), "information").observe(getActivity(), numberUnread -> {
            badgeDrawableInformation.setNumber(numberUnread);
            setBadgeDrawableVisible (numberUnread, badgeDrawableInformation);
        });
        announcementViewModel.getAnnouncementNumber(sp.getInt("userID", -1), "fake news").observe(getActivity(), numberUnread -> {
            badgeDrawableFakeNews.setNumber(numberUnread);
            setBadgeDrawableVisible (numberUnread, badgeDrawableFakeNews);
        });



        return root;
    }

    void setBadgeDrawableVisible (int numberUnread, BadgeDrawable badgeDrawable){
        if(numberUnread <= 0) {
            badgeDrawable.setVisible(false);
        }
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