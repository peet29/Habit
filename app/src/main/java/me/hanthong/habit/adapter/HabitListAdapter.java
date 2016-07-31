package me.hanthong.habit.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.hanthong.habit.R;
import me.hanthong.habit.data.DataColumns;

/**
 * Created by peet29 on 29/7/2559.
 */
public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.ViewHolder>{
    private static final String LOG_TAG = "HabitListAdapter";
    private Cursor mCursor;
    private Context mContext;
    private OnItemClickListener mListener;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView Title;
        public final ImageView itemImage;

        public ViewHolder(View v) {
            super(v);
            Title = (TextView) v.findViewById(R.id.habit_title);
            itemImage = (ImageView) v.findViewById(R.id.habit_image);
        }
    }

    public HabitListAdapter(Context context,OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_list_item, parent, false);
        v.setFocusable(true);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mCursor.moveToPosition(position);

        String imageName = mCursor.getString(mCursor.getColumnIndex(DataColumns.PIC));
        String itemName = mCursor.getString(mCursor.getColumnIndex(DataColumns.NAME));
        holder.Title.setText(itemName);

        int resID = mContext.getResources().getIdentifier(imageName , "drawable", mContext.getPackageName());
        Glide.with(mContext)
                .load(resID)
                .crossFade()
                .centerCrop()
                .into(holder.itemImage);


        holder.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);
            }
        });
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }


    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        //Log.d(LOG_TAG,"mCursor count = "+ mCursor.getCount());
        notifyDataSetChanged();
    }
}
