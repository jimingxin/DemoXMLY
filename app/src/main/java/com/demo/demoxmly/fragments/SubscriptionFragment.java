package com.demo.demoxmly.fragments;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.demoxmly.R;
import com.demo.demoxmly.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionFragment extends BaseFragment {


    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View history_view = layoutInflater.inflate(R.layout.fragment_subscription,null);

        return history_view;
    }
}
