package com.quickblox.sample.chat.persistence;

import android.content.Context;

import com.lvc.database.DatabaseHelper;
import com.lvc.database.ReflectionException;
import com.lvc.database.TableUtils;
import com.quickblox.sample.chat.model.ChatMessage;

/**
 * Created by andre.castro on 18/08/14.
 */
public class OpenHelperChat extends DatabaseHelper {

    public static final String DATA_BASE_NAME = "osmobile";
    private static final int DATA_BASE_VERSION = 7;

    public OpenHelperChat(Context context) {
        super(context, DATA_BASE_NAME, DATA_BASE_VERSION);
    }

    @Override
    public String[] getScriptsCreateDataBase() throws ReflectionException {
        String[] script = {
                TableUtils.createTableScript(ChatMessage.class),

        };
        return script;
    }

    @Override
    public String[] getScriptsUpdateDataBase() throws ReflectionException {
        String[] script = {
                TableUtils.createDropTableScript(ChatMessage.class),
        };
        return script;
    }
}