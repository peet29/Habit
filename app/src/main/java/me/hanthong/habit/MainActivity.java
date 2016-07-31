package me.hanthong.habit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;

import me.hanthong.habit.adapter.NavDrawerListAdapter;
import me.hanthong.habit.fragment.HabitFragment;
import me.hanthong.habit.fragment.PrepareDataFragment;

public class MainActivity extends AppCompatActivity implements NavDrawerListAdapter.OnItemClickListener {
    private static String LOG_TAG = "MainActivity";
    protected RecyclerView mRecyclerViewNavList;
    protected NavDrawerListAdapter mNavListAdapter;
    protected Context context;
    protected DrawerLayout mDrawer;
    private String[] mCategoryArray;
    private OurCustomReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mReceiver = new OurCustomReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("me.hanthong.habit");
        registerReceiver(mReceiver, intentFilter);

        mRecyclerViewNavList = (RecyclerView) findViewById(R.id.navigation_drawer_list);
        getJsonData();
        mNavListAdapter = new NavDrawerListAdapter(mCategoryArray, this);
        mRecyclerViewNavList.setAdapter(mNavListAdapter);
        mRecyclerViewNavList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PrepareDataFragment prepareDataFragment = new PrepareDataFragment();
        fragmentTransaction.replace(R.id.fragment, prepareDataFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getJsonData() {
        JSONArray data;
        try {
            data = new JSONArray(Utility.loadJSONFromAsset(getApplicationContext(), "category.json"));
            mCategoryArray = new String[data.length()];
            for (int i = 0; i < data.length(); i++) {
                mCategoryArray[i] = data.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public class OurCustomReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction() == "me.hanthong.habit") {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Log.d(LOG_TAG, Integer.toString(fragmentManager.getFragments().size()));
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                int position = sharedPref.getInt("category", 0);

                HabitFragment fragment = HabitFragment.newInstance(mCategoryArray[position]);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();


            }
        }

    }

    @Override
    public void onClick(View view, int position) {
        selectItem(position);

    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HabitFragment fragment = HabitFragment.newInstance(mCategoryArray[position]);
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("category",position);
        editor.commit();


        // update selected item title, then close the drawer
        Log.d(LOG_TAG, "Element " + position + " clicked.");
        Log.d(LOG_TAG, "Element " + mCategoryArray[position] + " clicked ");
        mDrawer.closeDrawer(GravityCompat.START);
    }
}
