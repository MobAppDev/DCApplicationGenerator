/*
 * @author: Shivani Nayar <shivani.nayar@asu.edu>
 * @date: Dec'12
 * 
 */

package com.project.DatabaseManager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class DatabaseProvider extends ContentProvider{

	//private DatabaseHelper sqlDB = null;
	private static final int DATABASE_VERSION = 1;	
	private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		//sqlDB = new DatabaseHelper(context);
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
