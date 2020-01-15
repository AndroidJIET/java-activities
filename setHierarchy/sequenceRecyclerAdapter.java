package com.example.generateid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.view.View.GONE;

public class sequenceRecyclerAdapter extends RecyclerView.Adapter<sequenceRecyclerAdapter.viewHolder> {

    //declare interface
    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    private Context mContext;
    private ArrayList<sequenceData> mSequence;

    public sequenceRecyclerAdapter(Context mContext, ArrayList<sequenceData> mSequence) {
        this.mContext = mContext;
        this.mSequence = mSequence;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mLayoutInflater.inflate(R.layout.sethierarchysinglenode, null);
        return new sequenceRecyclerAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        final sequenceData mSinglePost = mSequence.get(position);
        holder.mPostName.setText(mSinglePost.getName());
        if (position==0){
            holder.mImageView.setVisibility(GONE);
        }
        if (mSinglePost.isCloseClicked()) {
            holder.mPostName.setBackgroundResource(R.drawable.roundtexviewafterclose);
            holder.mClose.setImageResource(R.drawable.ic_add);
            holder.mImageView.setImageResource(R.drawable.ic_arrow_close);

        } else {
            holder.mPostName.setBackgroundResource(R.drawable.roundtextview);
            holder.mClose.setImageResource(R.drawable.ic_close);
            holder.mImageView.setImageResource(R.drawable.ic_arrow);

        }
        holder.mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mSequence.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView mPostName;
        ImageButton mClose;
        ImageView mImageView;

        private viewHolder(@NonNull final View itemView) {
            super(itemView);
            mPostName = itemView.findViewById(R.id.sethierarchysinglenodelayouttextView);
            mClose = itemView.findViewById(R.id.sethierarchysinglenodelayouttextViewimagebutton);
            mImageView = itemView.findViewById(R.id.sethierarchysinglenodelayouttextViewimageview);

        }


    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }



}

