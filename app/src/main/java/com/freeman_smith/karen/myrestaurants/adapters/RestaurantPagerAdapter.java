package com.freeman_smith.karen.myrestaurants.adapters;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.freeman_smith.karen.myrestaurants.models.Restaurant;
import com.freeman_smith.karen.myrestaurants.ui.RestaurantDetailFragment;

import java.util.ArrayList;

/**
 * Created by Guest on 11/29/16.
 */
public class RestaurantPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Restaurant> mRestaurants;

    public RestaurantPagerAdapter(FragmentManager fm, ArrayList<Restaurant> restaurants) {
        super(fm);
        mRestaurants = restaurants;
    }

    @Override
    public Fragment getItem(int position) {
        return RestaurantDetailFragment.newInstance(mRestaurants.get(position));
    }

    @Override
    public int getCount() {
        return mRestaurants.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRestaurants.get(position).getName();
    }
}