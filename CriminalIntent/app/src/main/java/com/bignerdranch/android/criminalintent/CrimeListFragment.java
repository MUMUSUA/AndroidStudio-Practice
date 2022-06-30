package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public class CrimeListFragment extends Fragment {


    private RecyclerView mCrimeRecyclerView;
    private MediaPlayer mMediaPlayer;
    private CrimeDbLab mCrimeDbLab;
    private SwipeRefreshLayout swipeRefresh;
    public CrimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimeDbLab=CrimeDbLab.getInstance(getActivity());
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
        swipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

//        updateUI();
        return v;
    }

//    private void updateUI(){
//        CrimeLab crimeLab=CrimeLab.get(getActivity());
//        List<Crime> crimes=crimeLab.getCrimes();
//
//        mAdapter=new CrimeAdapter(crimes);
//        mCrimeRecyclerView.setAdapter(mAdapter);


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
private  Crime mCrime;
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
        final Crime crime=mCrimes.get(position);
        mCrime=mCrimes.get(position);
//        holder.mTitleTextView.setText(crime.getTitle());
//        holder.mDataTextView.setText(crime.getDate().toString());
//        holder.mSolvedCheckBox.setChecked(crime.isSolved());

        holder.getTitleTextView().setText(crime.getTitle().toString());
        holder.getDataTextView().setText(crime.getDate().toString());
        holder.getSolvedCheckBox().setChecked(crime.isSolved());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
          //      Intent intent=new Intent(getActivity(),ShowActivity.class);
                Intent intent=new Intent(getActivity(),CrimeActivity.class);
                intent.putExtra("crime_id",mCrimes.get(position).getId());
                startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(CrimeListFragment.this.getActivity());
                mDialog.setTitle("删除");
                mDialog.setMessage("确定要删除吗?");
                mDialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Log.i("CrimeListFragment","------------------>position:"+position);
                        Log.i("CrimeListFragment","------------------>..............");
                        Toast.makeText(getActivity(),"删除成功!",Toast.LENGTH_SHORT).show();
                        Log.i("CrimeListFragment","------------------>uuid:"+mCrimes.get(position).getId());
                        mCrimeDbLab.deleteCrime(mCrimes.get(position).getId().toString());
                        mCrimes.remove(position);
                        startActivity(new Intent(getActivity(),CrimeListActivity.class));
                    }
                });
                mDialog.setNegativeButton("取消", null);
                mDialog.show();
                return true;
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
            case R.id.menu_item4:
                Toast.makeText(getActivity(),"Camera!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),ShowActivity.class));
                break;
            default:break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(getActivity(),CrimeListActivity.class));
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
