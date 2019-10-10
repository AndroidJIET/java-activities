/*
Created by Abhishek Kachhwaha for Grievance system on 10/10/2019
 */

package com.example.grievancesystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterForComplaintData extends RecyclerView.Adapter<RecyclerAdapterForComplaintData.ComplaintDataViewHolder> {

    private Context mContext;
    private ArrayList<ComplaintData> mComplaintData;
    LayoutInflater mLayoutInflater;
    private OnItemClicked onClick; // Interface in MainActivity for onclick listener

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }


    public RecyclerAdapterForComplaintData(Context mContext, ArrayList<ComplaintData> mComplaintData) {
        this.mContext = mContext;
        this.mComplaintData = mComplaintData;
    }



    @NonNull
    @Override
    public ComplaintDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        mLayoutInflater = (LayoutInflater)viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mLayoutInflater.inflate(R.layout.individualComplaintLayout,null);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(i);
            }
        });
        return new ComplaintDataViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull ComplaintDataViewHolder complaintDataViewHolder, final int i) {

        ComplaintData singleComplaintData = mComplaintData.get(i);
       
        complaintDataViewHolder.mTitle.setText(singleComplaintData.getTitle());
        complaintDataViewHolder.mDate.setText(singleComplaintData.getDate());
        complaintDataViewHolder.mDescription.setText(singleComplaintData.getDescription());
        complaintDataViewHolder.mUpVote.setText(singleComplaintData.getUpVote());
        complaintDataViewHolder.mStatus.setText(singleComplaintData.getStatus());

    }

    @Override
    public int getItemCount() {
        return mComplaintData.size();
    }

    class ComplaintDataViewHolder extends RecyclerView.ViewHolder{

        TextView mTitle;
        TextView mDate;
        TextView mDescription;
        TextView mUpVote;
        TextView mStatus;

        public ComplaintDataViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.individualComplaintTitle);
            mDate = itemView.findViewById(R.id.individualComplaintDate);
            mDescription = itemView.findViewById(R.id.individualComplaintDescription);
            mUpVote = itemView.findViewById(R.id.individuaComplaintUpVote);
            mStatus = itemView.findViewById(R.id.individualComplaintStatus);
        }
    }

    /* Code to add later to the MainPage after setting up the adapter to the recycler view the code consist of
       a interface implemetation and binding of listener with recycler adapter
     */

    /*
    Adaptername.setOnClick(MainPage.this); // binding the listener

    @Override
    public void onItemClick(int position) {
        //on click implementing of interface , specify actions here
    }
     */

}
