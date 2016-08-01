package me.hanthong.habit.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by peet29 on 14/7/2559.
 */
@Database(version = DataDatabase.VERSION)
public final class DataDatabase {
    public static final int VERSION = 2;

    @Table(DataColumns.class)
    public static final String LISTS = "lists";
}
