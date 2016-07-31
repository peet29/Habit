package me.hanthong.habit.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import me.hanthong.habit.DetailActivity;
import me.hanthong.habit.R;
import me.hanthong.habit.adapter.HabitListAdapter;
import me.hanthong.habit.data.DataColumns;
import me.hanthong.habit.data.DataProvider;


public class HabitFragment extends Fragment implements HabitListAdapter.OnItemClickListener,LoaderManager.LoaderCallbacks<Cursor>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String LOG_TAG =  "HabitFragment";
    private static final int HABIT_LOADER = 0;

    private ImageView mCategoryImage;
    private TextView mCategoryTitle;
    private RecyclerView mHabitList;
    private HabitListAdapter mAdapter;

    String[] PROJECTION = {
            DataColumns._ID,
            DataColumns.NAME,
            DataColumns.POSITION,
            DataColumns.CATEGORY,
            DataColumns.PIC,
            DataColumns.DATA,
            DataColumns.PIC_CATEGORY
    };


    // TODO: Rename and change types of parameters
    private String mCategory;


    public HabitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category a category of habit
     * @return A new instance of fragment HabitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitFragment newInstance(String category) {
        HabitFragment fragment = new HabitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_PARAM1);
        }
        LoaderManager LoaderManager = getLoaderManager();
        LoaderManager.initLoader(HABIT_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_habit, container, false);
        mHabitList = (RecyclerView) view.findViewById(R.id.habit_list);
        mAdapter = new HabitListAdapter(getActivity(),this);
        mHabitList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHabitList.setAdapter(mAdapter);
        mHabitList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());

        mCategoryTitle = (TextView) view.findViewById(R.id.habit_category_title);
        mCategoryImage = (ImageView) view.findViewById(R.id.habit_category_image);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String order  = DataColumns.POSITION+" ASC";
        Log.d(LOG_TAG, "mCategory = "+ mCategory);
        return new CursorLoader(getActivity(), DataProvider.Lists.withCategory(mCategory),PROJECTION,null,null,order);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG_TAG,"Load Finished");
        Log.d(LOG_TAG, "data count = "+Integer.toString(data.getCount()));
        Log.d(LOG_TAG,"data getColumnCount = "+data.getColumnCount());

        data.moveToFirst();
        mCategoryTitle.setText(data.getString(data.getColumnIndex(DataColumns.CATEGORY)));
        String imageName = data.getString(data.getColumnIndex(DataColumns.PIC_CATEGORY));
        int resID = getResources().getIdentifier(imageName , "drawable", getContext().getPackageName());
        Glide.with(getContext())
                .load(resID)
                .crossFade()
                .centerCrop()
                .into(mCategoryImage);
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments


        // update selected item title, then close the drawer
        Log.d(LOG_TAG, "Element " + position + " clicked.");
       // Log.d(LOG_TAG,"Element "+ mCategoryArray[position] +" clicked ");
        //mDrawer.closeDrawer(GravityCompat.START);

        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("category",mCategory);
        intent.putExtra("position",position);
        getActivity().startActivity(intent);
    }
}
