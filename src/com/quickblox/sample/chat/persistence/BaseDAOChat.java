package com.quickblox.sample.chat.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.lvc.database.BaseDAO;
import com.lvc.database.EntitiePersistable;

/**
 * Created by andre.castro on 18/08/14.
 */
public abstract class BaseDAOChat<T extends EntitiePersistable> extends BaseDAO<T> {

    protected Context context;

    public BaseDAOChat(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public SQLiteOpenHelper getDataBaseHelper() {
        return new OpenHelperChat(getContext());
    }

}
