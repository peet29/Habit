package me.hanthong.habit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.hanthong.habit.R;

/**
 * Created by peet29 on 26/7/2559.
 */
public class NavDrawerListAdapter extends RecyclerView.Adapter<NavDrawerListAdapter.ViewHolder> {
    private static final String LOG_TAG = "NavDrawerListAdapter";
    private String[] mData;
    private OnItemClickListener mListener;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView Title;
        public String c;
        public String Fav;

        public ViewHolder(View v) {
            super(v);
            final Context itemContext = v.getContext();
            final String intentKey = "news_id";
            // Define click listener for the ViewHolder's View.
           /* v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");

                }
            });*/
            Title = (TextView) v.findViewById(R.id.nav_item_title);
        }

        public TextView getTitle() {
            return Title;
        }
    }

    public NavDrawerListAdapter(String[] data ,OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nav_list_item, parent, false);
        v.setFocusable(true);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getTitle().setText(mData[position]);
        holder.getTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public void setmData(String[] data){
        mData = data;
        notifyDataSetChanged();
    }
}
