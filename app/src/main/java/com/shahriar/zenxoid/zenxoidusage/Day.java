package com.shahriar.zenxoid.zenxoidusage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.shahriar.zenxoid.zenxoidusage.GetherInfo.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class Day extends Fragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        main("day", getView(), this.getActivity());
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onResume() {
       main("day", getView(), this.getActivity());
        super.onResume();
    }
}