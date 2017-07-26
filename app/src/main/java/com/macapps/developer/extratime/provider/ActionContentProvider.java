package com.macapps.developer.extratime.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.macapps.developer.extratime.provider.ActionContract.ActionEntry;

/**
 * Created by Developer on 24/7/2017.
 */

public class ActionContentProvider extends ContentProvider {
    private static final String TAG=ActionContentProvider.class.getSimpleName();
    public static final int ACTIONS = 100;
    public static final int ACTIONS_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ActionContract.AUTHORITY,ActionContract.PATH_ACTIONS,ACTIONS);
        uriMatcher.addURI(ActionContract.AUTHORITY,ActionContract.PATH_ACTIONS+"/#",ACTIONS_WITH_ID);
        return uriMatcher;
    }
    private ActionDBHelper actionDBHelper;
    @Override
    public boolean onCreate() {
        Context context= getContext();
        actionDBHelper=new ActionDBHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db=actionDBHelper.getReadableDatabase();
        int match=sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match){
            case ACTIONS:
                retCursor=db.query(ActionEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
    /***
     * Handles requests to insert a single new row of data
     *
     * @param uri
     * @param values
     * @return
     */

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db =actionDBHelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case ACTIONS:
                long id=db.insert(ActionEntry.TABLE_NAME,null,values);
                if(id>0){
                    returnUri= ContentUris.withAppendedId(ActionEntry.CONTENT_URI,id);
                }else{
                    throw new android.database.SQLException("Failed To insert Row"+uri );
                }
                break;
            default:throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database=actionDBHelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        int actionsDeleted;
        switch (match){
            case ACTIONS_WITH_ID:
                String id=uri.getPathSegments().get(1);
                actionsDeleted=database.delete(ActionEntry.TABLE_NAME,"_id=?",new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("unknown uri: "+uri);

        }
        if(actionsDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }else Log.e(TAG,"No activity has been removed");
        return actionsDeleted;



    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase=actionDBHelper.getWritableDatabase();
        final int match=sUriMatcher.match(uri);
        int actionsUpdated;
        switch (match){
            case ACTIONS_WITH_ID:
                String id=uri.getPathSegments().get(1);
                //TODO cambiar columnACtionID si no funciona
                actionsUpdated=sqLiteDatabase.update(ActionEntry.TABLE_NAME,values,ActionEntry.COLUMN_ACTION_ID+"=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("unknown uri "+uri);
        }
        if(actionsUpdated!=0){getContext().getContentResolver().notifyChange(uri,null);

        }
        return actionsUpdated;
    }
}
