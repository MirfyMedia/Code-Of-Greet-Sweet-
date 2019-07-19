package com.greetsweet.plant.greetapp.SUBGREETfrag;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greetsweet.plant.PhotoApp;
import com.greetsweet.plant.R;
import com.greetsweet.plant.greetapp.Fragments.More.GridFragment;
import com.greetsweet.plant.mylibs.utility.Cons;

import java.util.ArrayList;
import java.util.List;


public class MainGridFragment extends Fragment {
View view;
    public MainGridFragment() {
        // Required empty public constructor
    }

    public static MainGridFragment newInstance(String param1, String param2) {
        MainGridFragment fragment = new MainGridFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_maingrid, container, false);


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new GridFragment(), "ALL");
        adapter.addFragment(new CorporateFragment(), "CORPORATE");

        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)view. findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        return  view;
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        PhotoApp.getInstance().createLog("WORKING 1---");
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        PhotoApp.getInstance().createLog("WORKING 2---");
        Integer.parseInt(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.category_id));
        Integer.parseInt(PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.subcategory_id));

        PhotoApp.getInstance().createLog("MAin grid check catid  "+PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.category_id));
        PhotoApp.getInstance().createLog("MAin grid check subcatid  "+PhotoApp.getInstance().getstoreSharedpref().getSharedValue(Cons.subcategory_id));

        adapter.addFragment(new GridFragment(), "ALL");
        PhotoApp.getInstance().createLog("WORKING 3---");

        adapter.addFragment(new CorporateFragment(), "CORPORATE");
        PhotoApp.getInstance().createLog("WORKING 4---");

        viewPager.setAdapter(adapter);
        PhotoApp.getInstance().createLog("WORKING 5---");

        TabLayout tabLayout = (TabLayout)view. findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        PhotoApp.getInstance().createLog("WORKING 6---");

    }
}
