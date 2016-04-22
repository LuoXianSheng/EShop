package com.newer.eshop.classify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newer.eshop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {

    private String text;

    public RightFragment(String text) {
        this.text = text;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        TextView tv = (TextView) view.findViewById(R.id.classify_right_tv);
        tv.setText(text);
        return view;
    }


}
