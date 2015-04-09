package com.enseirb.pfa.bastats.data.DAO.action;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.DAO.BaseDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayingTimeDAO;
import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.action.Rebound;

import java.util.ArrayList;
import java.util.List;

public class DBReboundDAO extends BaseDAO {

    public static final  String TABLE_NAME                  = "REBOUND";

    private static final String ID_FIELD_NAME               = "_ID";
    private static final String ACTION_ID_FIELD_NAME        = "ACTION_ID";
    private static final String TYPE_FIELD_NAME             = "TYPE";

    private static final String ID_FIELD_TYPE               = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String ACTION_ID_FIELD_TYPE        = "INTEGER";
    private static final String TYPE_FIELD_TYPE             = "INTEGER";

    private static final int NUM_COL_ID                     = 0;
    private static final int NUM_COL_ACTION                 = 1;
    private static final int NUM_COL_TYPE                   = 2;


    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
            + ", " + ACTION_ID_FIELD_NAME+ " " + ACTION_ID_FIELD_TYPE
            + ", " + TYPE_FIELD_NAME + " " + TYPE_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + ACTION_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBActionDAO.TABLE_NAME+"(ID) ";


    public DBReboundDAO(Context context){
	    super(context);
        this.mDb = this.open();
    }


    public String ReboundsJSON(int matchId, List<Player> players){
        StringBuilder str=new StringBuilder();


        for(Player j: players) {
            List<Rebound> rebounds =getAll(j,matchId);
            if(rebounds !=null) {
                for (Rebound rebound : rebounds) {
                    String reboundJson = ReboundJSON(rebound.getId());

                    str.append(reboundJson);

                }
            }
        }

        return str.toString();
    }

    public List<Rebound> getAll(Player j,int matchId){
        int playerId=j.getId();


        Cursor c = super.mDb.rawQuery("SELECT "+TABLE_NAME+".*"+
                        " FROM "+TABLE_NAME+" , "+DBActionDAO.TABLE_NAME+" , "+ DBPlayingTimeDAO.TABLE_NAME+
                        " WHERE " +DBActionDAO.TABLE_NAME+"."+DBActionDAO.PLAYER_ACTOR_ID_FIELD_NAME +"="+playerId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.MATCH_ID_FIELD_NAME+"="+matchId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.TEMPS_DE_JEU_FIELD_NAME
                        +" AND "+ DBReboundDAO.TABLE_NAME+"."+ DBReboundDAO.ACTION_ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.ID_FIELD_NAME
                ,
                null);
        return cursorToListRebound(c);

    }







    public String ReboundJSON(int id){
        StringBuilder str=new StringBuilder("{\"TypeAction\" :{");
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        c.moveToFirst();
        int actionId=c.getInt(NUM_COL_ACTION);
        DBActionDAO dba=new DBActionDAO(mCtx);

        String actionJSON=dba.ActionJSON(actionId);
        str.append("\""+"Type:"+"\": "+"\""+"REBOUND"+"\""+" ,\n");

        for(int i=2;i<c.getColumnCount();i++) {
            String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
        str.append("},\n");
        str.append(actionJSON);
        return str.toString();
    }












    public long insert(Rebound rebound){
        ContentValues values = new ContentValues();
        DBActionDAO dba=new DBActionDAO(mCtx);
        dba.insert(rebound);

        values.put(ACTION_ID_FIELD_NAME,dba.getLast().getActionId());
        values.put(TYPE_FIELD_NAME, rebound.getNature());
        return  mDb.insert(TABLE_NAME, null, values);
    }

    public long update(int id, Rebound rebound){
        ContentValues values = new ContentValues();

        values.put(TYPE_FIELD_NAME, rebound.getNature());
        return mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }


    public int removeWithId(int id){
        return mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);


    }


    public Rebound getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToRebound(c);
    }


    public List<Rebound> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                null, null, null, null, null);
        return cursorToListRebound(c);
    }


    private List<Rebound> cursorToListRebound(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Rebound> list = new ArrayList<Rebound>();
        list.clear();

        if (c.moveToFirst()) {
            do {
                Rebound rebound = new Rebound();
                rebound.setId(c.getInt(NUM_COL_ID));
                rebound.setActionId(c.getInt(NUM_COL_ACTION));
                rebound.setNature(c.getInt(NUM_COL_TYPE));
                list.add(rebound);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }


    private Rebound cursorToRebound(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Rebound rebound = new Rebound();

        rebound.setId(c.getInt(NUM_COL_ID));
        rebound.setActionId(c.getInt(NUM_COL_ACTION));
        rebound.setNature(c.getInt(NUM_COL_TYPE));

        c.close();

        return rebound;
    }



    public Rebound getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToRebound(c);
    }







}
