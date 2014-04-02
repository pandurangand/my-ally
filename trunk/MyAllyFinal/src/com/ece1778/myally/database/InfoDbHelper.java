package com.ece1778.myally.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ece1778.myally.database.InfoDb.ActivityEntry;
import com.ece1778.myally.database.InfoDb.CrisisEntry;
import com.ece1778.myally.database.InfoDb.DiaryEntry;
import com.ece1778.myally.dbt.diary.Emotion;
import com.ece1778.myally.dbt.diary.Urge;

public class InfoDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "UserInfo.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INT";

	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ ActivityEntry.TABLE_NAME
			+ " ("
			+ ActivityEntry._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ ActivityEntry.COLUMN_DATE
			+ TEXT_TYPE + " )";

	private static final String SQL_CREATE_CRISIS_ENTRY = "CREATE TABLE IF NOT EXISTS "
			+ CrisisEntry.TABLE_NAME
			+ " ("
			+ CrisisEntry._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ CrisisEntry.COLUMN_ACTIVITY + TEXT_TYPE + " )";

	private static final String SQL_CREATE_DIARY_ENTRY = "CREATE TABLE IF NOT EXISTS "
			+ DiaryEntry.TABLE_NAME
			+ " ("
			+ DiaryEntry._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ DiaryEntry.COLUMN_DATE
			+ TEXT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_ANGER
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_ANXIOUS
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_FEAR
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_HAPPY
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_MISERY
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_SAD
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_SHAME
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_ALCOHOL
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_DRUGS
			+ INT_TYPE
			+ ", "
			+ DiaryEntry.COLUMN_SELFHARM + TEXT_TYPE + " )";

	private static final String SQL_DELETE_ACTIVITY_ENTRIES = "DROP TABLE IF EXISTS "
			+ ActivityEntry.TABLE_NAME;

	private static final String SQL_DELETE_CRISIS_ENTRIES = "DROP TABLE IF EXISTS "
			+ CrisisEntry.TABLE_NAME;

	private static final String SQL_DELETE_DIARY_ENTRIES = "DROP TABLE IF EXISTS "
			+ DiaryEntry.TABLE_NAME;

	public InfoDbHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d("database", "infohelper");

	}

	public void onCreate(SQLiteDatabase db) {
		Log.d("database", "database oncreate");

		db.execSQL(SQL_CREATE_ENTRIES);
		db.execSQL(SQL_CREATE_CRISIS_ENTRY);
		db.execSQL(SQL_CREATE_DIARY_ENTRY);
		Log.d("database", "database oncreatedone");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ACTIVITY_ENTRIES);
		db.execSQL(SQL_DELETE_CRISIS_ENTRIES);
		db.execSQL(SQL_DELETE_DIARY_ENTRIES);

		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	public void updateOrAddCrisis(String crisisActivity) {
		Log.d("database", "database updateoradd");

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CrisisEntry._ID, 1);
		values.put(CrisisEntry.COLUMN_ACTIVITY, crisisActivity);

		db.replace(CrisisEntry.TABLE_NAME, null, values);
		Log.d("database", "database updateoradddone");

	}

	public String getCrisis() {
		Log.d("database", "getcrisis");

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(CrisisEntry.TABLE_NAME, CrisisEntry.COLUMNS,
				CrisisEntry._ID + " = 1", null, null, null, null, null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			Log.d("database", cursor.getString(0));

			Log.d("got crisis ", cursor.getString(0));

			return cursor.getString(0);
		} else {
			return "";
		}
	}

	public void updateOrAddDiaryEntry(HashMap<Emotion, Integer> emotionValues,
			HashMap<Urge, Integer> urgeValues) {
		Log.d("database", "database updateoradd");

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		values.put(DiaryEntry.COLUMN_DATE, date);
		values.put(DiaryEntry.COLUMN_ANGER, emotionValues.get(Emotion.ANGRY));
		values.put(DiaryEntry.COLUMN_ANXIOUS,
				emotionValues.get(Emotion.ANXIOUS));
		values.put(DiaryEntry.COLUMN_FEAR, emotionValues.get(Emotion.AFRAID));
		values.put(DiaryEntry.COLUMN_HAPPY, emotionValues.get(Emotion.HAPPY));
		values.put(DiaryEntry.COLUMN_MISERY,
				emotionValues.get(Emotion.MISERABLE));
		values.put(DiaryEntry.COLUMN_SAD, emotionValues.get(Emotion.SAD));
		values.put(DiaryEntry.COLUMN_SHAME, emotionValues.get(Emotion.ASHAMED));
		values.put(DiaryEntry.COLUMN_ALCOHOL, urgeValues.get(Urge.ALCOHOL));
		values.put(DiaryEntry.COLUMN_DRUGS, urgeValues.get(Urge.DRUGS));
		values.put(DiaryEntry.COLUMN_SELFHARM, urgeValues.get(Urge.SELF_HARM));

		// values.put(CrisisEntry.COLUMN_ACTIVITY, crisisActivity);

		db.replace(CrisisEntry.TABLE_NAME, null, values);
		Log.d("database", "database updateoradddone");

	}

}
