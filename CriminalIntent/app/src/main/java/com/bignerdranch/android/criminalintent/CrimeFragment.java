package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.text.Editable;
        import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.CompoundButton.OnCheckedChangeListener;
        import android.widget.EditText;
import android.widget.Toast;
import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private TextView mCount;
    private Button mSendCrimeButton;
    private Button mCreateCrimeButton;
    private Button mReportCrimeButton;
    private CrimeLab mCrimeLab;
    private SQLiteDatabase mDatabase;
    private CrimeDbLab mCrimeDbLab;

    public static final String EXTRA_CRIME_ID = "criminalintent.CRIME_ID";
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public CrimeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCrime = new Crime();
        mCrimeLab=CrimeLab.getInstance(CrimeFragment.this.getActivity());
        Intent intent=getActivity().getIntent();
        UUID crimeid=(UUID)intent.getSerializableExtra("crime_id");
        Log.i("CrimeFragment","------------------>"+crimeid);
        mCrime=mCrimeLab.getCrime(crimeid);

//            mCrimeLab=CrimeLab.getInstance(CrimeFragment.this.getActivity());
//        mDatabase=new CrimeBaseHelper(getActivity()).getWritableDatabase();
          mCrimeDbLab=CrimeDbLab.getInstance(getActivity());
        Log.i("CrimeFragment","------------------>CrimeBaseHelperAfter");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);
        mCount=(TextView)v.findViewById(R.id.count_TextView);
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        if(mCrime.getTitle()==null)
            mCount.setText("0");
        else
            mCount.setText(Integer.toString(mCrime.getTitle().length()));

        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mCrime.setTitle(c.toString());
                mCount.setText(Integer.toString(c.length()));
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank

            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);


        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });


        mCreateCrimeButton=(Button)v.findViewById(R.id.create_crime_Button);
        mCreateCrimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCrime=new Crime();
                mTitleField.setText("");
                mDateButton.setText(mCrime.getDate().toString());
                mSolvedCheckBox.setChecked(false);

            }
        });


        mSendCrimeButton=(Button)v.findViewById(R.id.send_crime_Button);
        mSendCrimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                if(mCrimeLab.getCrimes().contains(mCrime)){
//                    mCrimeLab.getCrimes().remove(mCrime);
//                }
//                mCrimeLab.getCrimes().add(mCrime);
//                for(Crime mCrime:mCrimeLab.getCrimes()){
//                Log.i("Crime","------------------>crime"+mCrime.getId().toString());
//                }
//
//                Log.i("CrimeFragment","------------------>SendBefore");
//                mCrimeDbLab.addCrime(mCrime);
//                Log.i("CrimeFragment","------------------>SendAfter");


                Cursor cursor=mCrimeDbLab.queryCrime(mCrime);
                cursor.moveToFirst();
                if(cursor.getCount()==0){
                    Log.i("CrimeFragment","------------------>SendBefore");
                    mCrimeDbLab.addCrime(mCrime);
                Log.i("CrimeFragment","------------------>SendAfter");}
                else{
                    Log.i("CrimeFragment","------------------>UpdateBefore");
                    mCrimeDbLab.updateCrime(mCrime);
                    Log.i("CrimeFragment","------------------>UpdateAfter");}



//                mCrimeLab.getCrimes().add(mCrime);
//                for(Crime mCrime:mCrimeLab.getCrimes()){
//                    Log.i("Crime","------------------>crime"+mCrime.getId().toString());
//
//                }

//                Log.i("CrimeFragment","------------------>SendBefore");
//                mCrimeDbLab.addCrime(mCrime);
//                Log.i("CrimeFragment","------------------>SendAfter");
            }
        });

        mReportCrimeButton=(Button)v.findViewById(R.id.report_crime_Button);
        mReportCrimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

//             startActivity(new Intent(getActivity(),CrimeListActivity.class));

                //Database
//
                mCrimeLab.getCrimes().clear();
                Log.i("CrimeFragment","------------------>mCrimeLab.getCrimes().size():"+mCrimeLab.getCrimes().size());
                Cursor cursor=mCrimeDbLab.queryCrimes(null,null);
                if(cursor==null)
                    Toast.makeText(getActivity(),"cursor is null!",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(getActivity(),"cursor's size is"+cursor.getCount(),Toast.LENGTH_SHORT).show();
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast()){

                        Log.i("CrimeFragment","title:"+cursor.getString(cursor.getColumnIndex("title")));
                        Log.i("CrimeFragment","------------------>date:"+cursor.getLong(cursor.getColumnIndex("date")));
                        Crime c=new Crime();
                        c.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex("uuid"))));
                        c.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        c.setDate(new Date(cursor.getLong(cursor.getColumnIndex("date"))));
                        c.setSolved(cursor.getInt(cursor.getColumnIndex("solved"))!=0);


                        cursor.moveToNext();
                        mCrimeLab.getCrimes().add(c);
                    }
                }

                startActivity(new Intent(getActivity(),CrimeListActivity.class));
                Log.i("CrimeFragment","------------------>CrimeListActivity");

            }
        });

        return v;
    }
}