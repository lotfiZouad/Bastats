package com.enseirb.pfa.bastats.data.DAO.action;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.DAO.BaseDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayerDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayingTimeDAO;
import com.enseirb.pfa.bastats.data.model.action.Action;

import java.util.ArrayList;
import java.util.List;


public class DBActionDAO extends BaseDAO {

    public static final String TABLE_NAME                   = "ACTION";

    public static final String ID_FIELD_NAME               = "_ID";
    public static final String TEMPS_DE_JEU_FIELD_NAME     = "PLAYING_TIME_ID";
    public static final String PLAYER_ACTOR_ID_FIELD_NAME  = "PLAYER_ACTOR_ID";
    public static final String COMMENT_FIELD_NAME          = "COMMENT";
    public static final String TYPE_FIELD_NAME             = "TYPE";

    private static final String ID_FIELD_TYPE               = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String PLAYING_TIME_FIELD_TYPE     = "INTEGER";
    private static final String PLAYER_ACTOR_ID_FIELD_TYPE  = "INTEGER";
    private static final String COMMENT_FIELD_TYPE          = "TEXT";
    private static final String TYPE_FIELD_TYPE             = "INTEGER";

    private static final int NUM_COL_ID          = 0;
    private static final int NUM_COL_PT          = 1;
    private static final int NUM_COL_ACTOR       = 2;
    private static final int NUM_COL_COMMENT     = 3;
    private static final int NUM_COL_TYPE        = 4;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
            + ", " + TEMPS_DE_JEU_FIELD_NAME     + " " + PLAYING_TIME_FIELD_TYPE
            + ", " + PLAYER_ACTOR_ID_FIELD_NAME + " " + PLAYER_ACTOR_ID_FIELD_TYPE
            + ", " + COMMENT_FIELD_NAME + " " + COMMENT_FIELD_TYPE
            + ", " + TYPE_FIELD_NAME             + " " + TYPE_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + TEMPS_DE_JEU_FIELD_NAME+") "
            + "REFERENCES "+ DBPlayingTimeDAO.TABLE_NAME+"(ID)"
            + ", " + "FOREIGN KEY (" + PLAYER_ACTOR_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBPlayerDAO.TABLE_NAME+"(ID)";


    public DBActionDAO (Context context){
        super(context);
        this.mDb = this.open();
    }

    public long insert(Action action){
        ContentValues values = new ContentValues();

        values.put(TEMPS_DE_JEU_FIELD_NAME, action.getPlayingTime());
        values.put(PLAYER_ACTOR_ID_FIELD_NAME, action.getActorPlayer());
        values.put(COMMENT_FIELD_NAME, action.getComment());
        values.put(TYPE_FIELD_NAME, action.getType());

        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public String ActionJSON(int id){
        StringBuilder str=new StringBuilder("\"action\" :{");
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        c.moveToFirst();
        int tpsId=c.getInt(NUM_COL_PT);
        DBPlayingTimeDAO tps=new DBPlayingTimeDAO(mCtx);
        String tpsJson=tps.PlayingTimeJSON(tpsId);
        for(int i=2;i<c.getColumnCount();i++) {
            String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
        str.append("},\n");
        str.append(tpsJson);
        return str.toString();
    }




    public int update(int id, Action action){
        ContentValues values = new ContentValues();

        values.put(TEMPS_DE_JEU_FIELD_NAME, action.getPlayingTime());
        values.put(PLAYER_ACTOR_ID_FIELD_NAME, action.getActorPlayer());
        values.put(COMMENT_FIELD_NAME, action.getComment());
        values.put(TYPE_FIELD_NAME, action.getType());

        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }

    public int removeWithId(int id){
        return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public int removeWithId(String table,int id){
        Cursor c=super.mDb.query(table,new String[] {"ACTION_ID"},"_ID ="+id,null,null,null,null);
        c.moveToFirst();
        int actionId=c.getInt(0);
        Action action=getWithId(actionId);
        DBPlayingTimeDAO tps=new DBPlayingTimeDAO(mCtx);

        int i= super.mDb.delete(table, ID_FIELD_NAME + " = " +id, null);
        tps.removeWithId(action.getPlayingTime());

        mDb.execSQL("DELETE " + " FROM ACTION "+ " WHERE _ID ="+actionId);
        return i;
    }

    public Action getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToAction(c);
    }

    public List<Action> getActionsPlayer(int joueurId){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                PLAYER_ACTOR_ID_FIELD_NAME +"=" + joueurId, null, null, null, null);
        return cursorToListAction(c);

    }

    public List<Action> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null , null, null, null, null, null);
        return cursorToListAction(c);
    }

    private List<Action> cursorToListAction(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Action> listActions = new ArrayList<Action>();
        listActions.clear();

        if (c.moveToFirst()) {
            do {
                Action action = new Action();
                action.setActionId(c.getInt(NUM_COL_ID));
                action.setActorPlayer(c.getInt(NUM_COL_ACTOR));
                action.setPlayingTime(c.getInt(NUM_COL_PT));
                action.setComment(c.getString(NUM_COL_COMMENT));
                action.setType(c.getInt(NUM_COL_TYPE));
                listActions.add(action);
            } while (c.moveToNext());
        }
        c.close();
        return listActions;
    }

    private Action cursorToAction(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Action action = new Action();
        action.setActionId(c.getInt(NUM_COL_ID));
        action.setActorPlayer(c.getInt(NUM_COL_ACTOR));
        action.setPlayingTime(c.getInt(NUM_COL_PT));
        action.setComment(c.getString(NUM_COL_COMMENT));
        action.setType(c.getInt(NUM_COL_TYPE));
        c.close();

        return action;
    }

    public Action getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToAction(c);
    }



}
