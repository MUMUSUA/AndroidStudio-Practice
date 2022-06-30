package com.bignerdranch.android.crimerecord;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeDbLab mCrimeDbLab;
    private MediaPlayer mMediaPlayer;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mCrimeRecyclerView=(RecyclerView)v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCrimeRecyclerView.setAdapter(new CrimeAdapter(CrimeLab.getInstance().getCrimes(),getContext()));
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
//    }







private class CrimeHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView mTitleTextView;
        private TextView mDetailTextView;
        private ImageView mImageView;
        private Button share;
        private Button readMore;
        public TextView getTitleTextView() {
            return mTitleTextView;
        }

        public TextView getDetailTextView() {
            return mDetailTextView;
        }

        public ImageView getImageView() {
        return mImageView;
    }


        public CrimeHolder(View itemView){

            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            mTitleTextView=(TextView)itemView.findViewById(R.id.painting_title);
           mDetailTextView=(TextView)itemView.findViewById(R.id.painting_desc);
           mImageView = (ImageView) itemView.findViewById(R.id.painting_photo);
            share = (Button) itemView.findViewById(R.id.btn_share);
            readMore = (Button) itemView.findViewById(R.id.btn_more);

            // 设置TextView背景为半透明
            mTitleTextView.setBackgroundColor(Color.argb(20, 0, 0, 0));
        }
    }



private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
    private List<Crime> mCrimes;
    private Context context;
//    public CrimeAdapter(List<Crime> crimes){
//        mCrimes=crimes;
//    }
    public CrimeAdapter(List<Crime> newses, Context context) {
        super();
        this.mCrimes = newses;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return mCrimes.size();
    }

    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
//        View view=layoutInflater.inflate(R.layout.list_item_crime,parent,false);

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_crime,
                null);
        CrimeHolder nvh = new CrimeHolder(view);

//        return new CrimeHolder(view);
        return  nvh;
    }

    @Override
    public void onBindViewHolder(CrimeHolder holder, final int position) {
        final int j = position;
        Crime crime=mCrimes.get(position);
        holder.mTitleTextView.setText(crime.getTitle());
        holder.mDetailTextView.setText(crime.getDesc());
        holder.mImageView.setImageBitmap(crime.getPhotoId());
//        holder.mSolvedCheckBox.setChecked(crime.isSolved());

        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ShowActivity.class);
                Intent intent=new Intent(getActivity(),EnjoyActivity.class);
//                Intent intent=new Intent(getActivity(),CrimeActivity.class);
                intent.putExtra("crime_id",mCrimes.get(position).getId());
//                intent.putExtra("Painting",mCrimes.get(j));
                startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
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
                       // startActivity(new Intent(getActivity(),CrimeListActivity.class));
                    }
                });
                mDialog.setNegativeButton("取消", null);
                mDialog.show();
                return true;
            }
        });


        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, mCrimes.get(j).getDesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, mCrimes
                        .get(j).getTitle()));
            }
        });

        holder.readMore
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),EnjoyActivity.class);
//                Intent intent=new Intent(getActivity(),CrimeActivity.class);
                        intent.putExtra("crime_id",mCrimes.get(position).getId());
//                intent.putExtra("Painting",mCrimes.get(j));
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
                startActivity(new Intent(getActivity(),CrimeActivity.class));

                break;
            case R.id.menu_item1:
                Toast.makeText(getActivity(),"Music stop!",Toast.LENGTH_SHORT).show();
               getActivity().stopService(new Intent(getActivity(),MusicPlayService.class));
//                mMediaPlayer.stop();
                break;
            case R.id.menu_item5:
                Toast.makeText(getActivity(),"Music play!",Toast.LENGTH_SHORT).show();
//                mMediaPlayer=MediaPlayer.create(getActivity(),R.raw.melody);
                getActivity().startService(new Intent(getActivity(),MusicPlayService.class));
//                mMediaPlayer.start();
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
                    Thread.sleep(700);
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

    public Bitmap convertToBitmap(String base64String) {

        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        Bitmap bitmapResult = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return bitmapResult;

    }
}
