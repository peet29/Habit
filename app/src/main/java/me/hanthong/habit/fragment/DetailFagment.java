package me.hanthong.habit.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.hanthong.habit.R;
import me.hanthong.habit.data.DataColumns;
import me.hanthong.habit.data.DataProvider;

public class DetailFagment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LOG_TAG = "DetailFagment";
    private static final String ARG_PARAM1 = "category";
    private static final String ARG_PARAM2 = "position";
    private static final int DETAIL_LOADER = 1;

    // TODO: Rename and change types of parameters
    private String mCategory;
    private String mTitle;
    private String mData;
    private int mPosition;

    protected ImageView mCateImage;
    protected TextView mTextTitle;
    protected TextView mTextData;

    String[] PROJECTION = {
            DataColumns._ID,
            DataColumns.NAME,
            DataColumns.POSITION,
            DataColumns.CATEGORY,
            DataColumns.PIC,
            DataColumns.DATA,
            DataColumns.PIC_CATEGORY
    };


    public DetailFagment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1.
     * @param position Parameter 2.
     * @return A new instance of fragment DetailFagment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFagment newInstance(String category, int position) {
        DetailFagment fragment = new DetailFagment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, category);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_PARAM1);
            mPosition = getArguments().getInt(ARG_PARAM2);
        }

        LoaderManager LoaderManager = getLoaderManager();
        LoaderManager.initLoader(DETAIL_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_detail_fagment, container, false);

        mCateImage = (ImageView) view.findViewById(R.id.detail_image);
        mTextTitle = (TextView) view.findViewById(R.id.detail_title);
        mTextData = (TextView) view.findViewById(R.id.detail_data);

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareData();
            }
        });

        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String select = DataColumns.POSITION + " = ?";
        String position = Integer.toString(mPosition);
        return new CursorLoader(getContext(), DataProvider.Lists.withCategory(mCategory),
                PROJECTION,
                select,
                new String[]{position},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        mTitle = data.getString(data.getColumnIndex(DataColumns.NAME));
        mData = data.getString(data.getColumnIndex(DataColumns.DATA));
        mTextTitle.setText(mTitle);
        mTextData.setText(mData);
        String imageName = data.getString(data.getColumnIndex(DataColumns.PIC));
        int resID = getResources().getIdentifier(imageName , "drawable", getContext().getPackageName());
        Glide.with(getContext())
                .load(resID)
                .crossFade()
                .centerCrop()
                .into(mCateImage);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void shareData()
    {
        ShareCompat.IntentBuilder
                .from(getActivity()) // getActivity() or activity field if within Fragment
                .setText(mTitle+"\n\n"+mData)
                .setType("text/plain") // most general text sharing MIME type
                .setChooserTitle(getString(R.string.share_title))
                .startChooser();
    }
}
