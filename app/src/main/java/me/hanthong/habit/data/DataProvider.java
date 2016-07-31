package me.hanthong.habit.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by peet29 on 14/7/2559.
 */
@ContentProvider(authority = DataProvider.AUTHORITY,database = DataDatabase.class)
public final class DataProvider {

    public static final String AUTHORITY = "me.hanthong.habit.data.DataProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String LISTS = "lists";
    }

    @TableEndpoint(table = DataDatabase.LISTS)
    public static class Lists {

        @ContentUri(
                path = "lists",
                type = "vnd.android.cursor.dir/list",
                defaultSort = DataColumns.POSITION + " ASC")
        public static final Uri LISTS = Uri.parse("content://" + AUTHORITY + "/lists");

        @InexactContentUri(
                path = Path.LISTS + "/#",
                name = "LIST_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = DataColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/lists/" + id);
        }

        @InexactContentUri(
                path = Path.LISTS + "/*",
                name = "LIST_CATEGORY",
                type = "vnd.android.cursor.item/list",
                whereColumn = DataColumns.CATEGORY,
                defaultSort = DataColumns.POSITION + " ASC",
                pathSegment = 1)
        public static Uri withCategory(String name) {
            return Uri.parse("content://" + AUTHORITY + "/lists/" + name);
        }

    }
}
