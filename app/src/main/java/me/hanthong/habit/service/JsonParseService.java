package me.hanthong.habit.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.hanthong.habit.Utility;
import me.hanthong.habit.data.DataColumns;
import me.hanthong.habit.data.DataProvider;

/**
 * Created by peet29 on 14/7/2559.
 */
public class JsonParseService extends IntentService {

    final String LOG_TAG = "JsonParseService";
    ContentResolver mContentResolver;

    public JsonParseService() {
        super("JsonParseService");
    }

    public final class Constants {
        public static final String BROADCAST_ACTION =
                "me.hanthong.habit.BROADCAST";
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mContentResolver = getContentResolver();

        Cursor check = mContentResolver.query(DataProvider.Lists.LISTS,new String[]{ DataColumns._ID},null,null,null);
        try {
            if (check.getCount() == 0 || check == null) {
                String jsonData = Utility.loadJSONFromAsset(getApplicationContext(),"data.json");
                JSONArray obj = new JSONArray(jsonData);
                ArrayList<ContentValues> cvArray = new ArrayList<>();
                for (int i = 0; i < obj.length(); i++) {
                    JSONObject data = (JSONObject) obj.get(i);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DataColumns.CATEGORY, data.getString("category"));
                    contentValues.put(DataColumns.POSITION, data.getInt("position"));
                    contentValues.put(DataColumns.NAME, data.getString("name"));
                    contentValues.put(DataColumns.DATA, data.getString("data"));
                    contentValues.put(DataColumns.PIC, data.getString("pic"));
                    contentValues.put(DataColumns.PIC_CATEGORY, data.getString("pic_category"));
                    cvArray.add(contentValues);
                }
                ContentValues[] cc = new ContentValues[cvArray.size()];
                cvArray.toArray(cc);
                mContentResolver.bulkInsert(DataProvider.Lists.LISTS, cc);
                Log.d(LOG_TAG,"count = "+cc.length);
                Log.d(LOG_TAG,"put data in content provider");

                Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            }else {
                Log.d(LOG_TAG,"Already have data");
            }
            check.close();
            onDone();
        } catch (JSONException e) {
            Log.e(LOG_TAG, "can't parse json");
        }
    }

    private void onDone(){
        Intent broadcast = new Intent();
        broadcast.setAction("me.hanthong.habit");
        sendBroadcast(broadcast);
    }
}
