package com.bignerdranch.android.mediaplayer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {
    private Button mStartButton;
    private Button mStopButton;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        mStartButton=(Button)view.findViewById(R.id.MusicPlay);

        mStopButton=(Button)view.findViewById(R.id.MusicStop);
mStartButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(),"Music play!",Toast.LENGTH_SHORT).show();
//                mMediaPlayer=MediaPlayer.create(getActivity(),R.raw.melody);
        getActivity().startService(new Intent(getActivity(),MusicPlayService.class));

    }
});



        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Music stop!",Toast.LENGTH_SHORT).show();
                getActivity().stopService(new Intent(getActivity(),MusicPlayService.class));
//
            }
        });
        return view;
    }

}
