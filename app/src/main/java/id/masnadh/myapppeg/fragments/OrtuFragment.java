package id.masnadh.myapppeg.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.masnadh.myapppeg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrtuFragment extends Fragment {


    public OrtuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ortu, container, false);
    }

}
