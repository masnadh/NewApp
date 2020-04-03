package id.masnadh.myapppeg.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import id.masnadh.myapppeg.fragments.AnakFragment;
import id.masnadh.myapppeg.fragments.DataFragment;
import id.masnadh.myapppeg.fragments.GolFragment;
import id.masnadh.myapppeg.fragments.OrtuFragment;
import id.masnadh.myapppeg.fragments.PangkatFragment;
import id.masnadh.myapppeg.fragments.PasutriFragment;

public class Pager2 extends FragmentStatePagerAdapter {

    private int number_tabs;

    public Pager2(@NonNull FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GolFragment();
            case 1:
                return new PangkatFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return number_tabs;
    }
}
