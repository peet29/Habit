package me.hanthong.habit.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by peet29 on 14/7/2559.
 */
public interface DataColumns {
    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(TEXT)
    @NotNull
    String CATEGORY = "category";

    @DataType(INTEGER)
    @NotNull
    String POSITION = "position";

    @DataType(TEXT)
    @NotNull
    String NAME = "name";

    @DataType(TEXT)
    @NotNull
    String DATA = "data";

    @DataType(TEXT)
    @NotNull
    String PIC = "pic";

    @DataType(TEXT)
    @NotNull
    String PIC_CATEGORY = "pic_category";
}
