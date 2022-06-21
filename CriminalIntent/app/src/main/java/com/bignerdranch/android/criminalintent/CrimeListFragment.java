package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class CrimeListFragment extends Fragment {


    private RecyclerView mCrimeRecyclerView;
    private MediaPlayer mMediaPlayer;

    public CrimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView=(RecyclerView)v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCrimeRecyclerView.setAdapter(new CrimeAdapter(CrimeLab.getInstance().getCrimes()));


//        updateUI();
        return v;
    }

//    private void updateUI(){
//        CrimeLab crimeLab=CrimeLab.get(getActivity());
//        List<Crime> crimes=crimeLab.getCrimes();
//
//        mAdapter=new CrimeAdapter(crimes);
//        mCrimeRecyclerView.setAdapter(mAdapter);
//    }


private class CrimeHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTextView;
        private TextView mDataTextView;
        private CheckBox mSolvedCheckBox;

        public TextView getTitleTextView() {
            return mTitleTextView;
        }

        public TextView getDataTextView() {
            return mDataTextView;
        }

        public CheckBox getSolvedCheckBox() {
            return mSolvedCheckBox;
        }

        public CrimeHolder(View itemView){

            super(itemView);
            mTitleTextView=(TextView)itemView.findViewById(R.id.crime_list_item_titleTextView);
            mDataTextView=(TextView)itemView.findViewById(R.id.crime_list_item_dateTextView);
            mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.crime_list_item_solvedCheckBox);

        }
    }



private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
    private List<Crime> mCrimes;

    public CrimeAdapter(List<Crime> crimes){
        mCrimes=crimes;
    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }

    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
        View view=layoutInflater.inflate(R.layout.list_item_crime,parent,false);
        return new CrimeHolder(view);
    }

    @Override
    public void onBindViewHolder(CrimeHolder holder, final int position) {
        Crime crime=mCrimes.get(position);
        holder.mTitleTextView.setText(crime.getTitle());
        holder.mDataTextView.setText(crime.getDate().toString());
        holder.mSolvedCheckBox.setChecked(crime.isSolved());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),ShowActivity.class);
//                Intent intent=new Intent(getActivity(),CrimeActivity.class);
//                intent.putExtra("crime_id",mCrimes.get(position).getId());
                startActivity(intent);
            }
        });
    }
}


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item0:
                Toast.makeText(getActivity(),"Music play!",Toast.LENGTH_SHORT).show();
//                mMediaPlayer=MediaPlayer.create(getActivity(),R.raw.melody);
                  getActivity().startService(new Intent(getActivity(),MusicPlayService.class));
//                mMediaPlayer.start();
                break;
            case R.id.menu_item1:
                Toast.makeText(getActivity(),"Music stop!",Toast.LENGTH_SHORT).show();
               getActivity().stopService(new Intent(getActivity(),MusicPlayService.class));
//                mMediaPlayer.stop();
                break;
            case R.id.menu_item2:
                Toast.makeText(getActivity(),"Browser",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),BrowserIntentWebActivity.class));
                break;

            case R.id.menu_item3:
                Toast.makeText(getActivity(),"PlayVideo!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),VideoPlayActivity.class));
                break;
            default:break;

        }
        return super.onOptionsItemSelected(item);
    }
}
