package com.newer.eshop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.newer.eshop.R;
import com.newer.eshop.account.LoginActivity;
import com.newer.eshop.goods.GoodsActivity;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentMe extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        Button login = (Button) view.findViewById(R.id.login);
        Button goods = (Button) view.findViewById(R.id.goods);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GoodsActivity.class));
            }
        });
        return view;
    }

}
