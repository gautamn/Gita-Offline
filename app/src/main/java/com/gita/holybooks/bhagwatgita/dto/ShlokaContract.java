package com.gita.holybooks.bhagwatgita.dto;

import android.provider.BaseColumns;

/**
 * Created by Nitin Gautam on 2/17/2018.
 */

public class ShlokaContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ShlokaContract() {}

    /* Inner class that defines the table contents */
    public static class ShlokaEntry implements BaseColumns {
        public static final String TABLE_NAME = "shloka";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SHLOKA_ID = "shloka_id";
        public static final String COLUMN_NAME_TEXT = "text";
    }
}
