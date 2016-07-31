package me.hanthong.habit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.hanthong.habit.R;
import me.hanthong.habit.service.JsonParseService;

public class PrepareDataFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prepare_data, container, false);
        Intent jsonIntent = new Intent(getContext(), JsonParseService.class);
        getActivity().startService(jsonIntent);
        return rootView;
    }
}
