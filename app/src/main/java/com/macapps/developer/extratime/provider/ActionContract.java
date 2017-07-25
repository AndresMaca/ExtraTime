package com.macapps.developer.extratime.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Developer on 24/7/2017.
 */

public class ActionContract {
    public static final String AUTHORITY="com.macapps.developer.extratime.provider";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+AUTHORITY);
    public static final String PATH_ACTIONS="actions";

    public static final class ActionEntry implements BaseColumns{
        public static final Uri CONTENT_URI= BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTIONS).build();
        public static final String TABLE_NAME="actions";
        public static final String COLUMN_ACTION_ID="actionID";
        public static final String COLUMN_ACTION_LOGO="actionLOGO";
        public static final String COLUMN_ACTION_HOURS="actionHOURS";


    }


}
