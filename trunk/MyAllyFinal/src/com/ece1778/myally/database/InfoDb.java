package com.ece1778.myally.database;

import android.provider.BaseColumns;

public final class InfoDb {

	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public InfoDb() {}

    /* Inner class that defines the table contents */
    public static abstract class ActivityEntry implements BaseColumns {
        public static final String TABLE_NAME = "ActivityInfo";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PRE_MOOD = "premood";
        public static final String COLUMN_PRE_HR = "preheartrate";
        public static final String COLUMN_ACTIVITY = "activity";
        public static final String COLUMN_POST_MOOD = "postmood";
        public static final String COLUMN_POST_HR = "postheartrate";

    }
    
    /* Inner class that defines the table contents */
    public static abstract class DiaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "DiaryInfo";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ANGER = "anger";
        public static final String COLUMN_ANXIOUS = "anxious";
        public static final String COLUMN_FEAR = "fear";
        public static final String COLUMN_HAPPY = "happy";
        public static final String COLUMN_MISERY = "misery";
        public static final String COLUMN_SAD = "sad";
        public static final String COLUMN_SHAME = "shame";
        
        public static final String COLUMN_ALCOHOL = "alcohol";
        public static final String COLUMN_DRUGS = "drugs";
        public static final String COLUMN_SELFHARM = "selfharm";

    }
    
    /* Inner class that defines the table contents */
    public static abstract class CrisisEntry implements BaseColumns {
        public static final String TABLE_NAME = "CrisisInfo";
        public static final String COLUMN_ACTIVITY = "activity";
        
        public static final String[] COLUMNS = {COLUMN_ACTIVITY};
    }
}