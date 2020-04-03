package id.masnadh.myapppeg.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import id.masnadh.myapppeg.fragments.AnakFragment;
import id.masnadh.myapppeg.fragments.DataFragment;
import id.masnadh.myapppeg.fragments.GolFragment;
import id.masnadh.myapppeg.fragments.OrtuFragment;
import id.masnadh.myapppeg.fragments.PangkatFragment;
import id.masnadh.myapppeg.fragments.PasutriFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int number_tabs;

    public PagerAdapter(FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }

    //Mengembalikan Fragment yang terkait dengan posisi tertentu
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DataFragment();
            case 1:
                return new PasutriFragment();
            case 2:
                return new AnakFragment();
            case 3:
                return new OrtuFragment();
            default:
                return null;
        }
    }

    //Mengembalikan jumlah tampilan yang tersedia.
    @Override
    public int getCount() {
        return number_tabs;
    }

}
