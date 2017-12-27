package com.dlwx.wisdomschool.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dlwx.wisdomschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivateChatFragment extends Fragment {


    public PrivateChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_private_chat, container, false);
    }

}
