package com.enseirb.pfa.bastats.data.DAO.action;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.DAO.BaseDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayerDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayingTimeDAO;
import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.action.Assist;

import java.util.ArrayList;
import java.util.List;

public class DBAssistDAO extends BaseDAO {

    public static final String TABLE_NAME = "ASSIST";

    private static final String ID_FIELD_NAME               = "_ID";
    private static final String ACTION_ID_FIELD_NAME        = "ACTION_ID";
    private static final String PLAYER_TARGETED_ID_FIELD_NAME = "PLAYER_TARGETED_ID";

    private static final String ID_FIELD_TYPE               = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String ACTION_ID_FIELD_TYPE        = "INTEGER";
    private static final String PLAYER_TARGETED_ID_FIELD_TYPE = "INTEGER";


    private static final int NUM_COL_ID                     = 0;
    private static final int NUM_COL_ACTION                 = 2;
    private static final int NUM_COL_PLAYER_TARGETED_ID     = 1;


    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
            + ", " + PLAYER_TARGETED_ID_FIELD_NAME + " " + PLAYER_TARGETED_ID_FIELD_TYPE
            + ", " + ACTION_ID_FIELD_NAME+ " " + ACTION_ID_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + PLAYER_TARGETED_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBPlayerDAO.TABLE_NAME+"(ID)"
            + ", " + "FOREIGN KEY (" + ACTION_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBActionDAO.TABLE_NAME+"(ID) ";

    public DBAssistDAO(Context context) {
        super(context);
        this.mDb = this.open();
    }


    public String AssistsJSON(int matchId, List<Player> players){
        StringBuilder str=new StringBuilder();


        for(Player j: players) {
            List<Assist> assists=getAll(j,matchId);
            if(assists!=null) {
                for (Assist assist : assists) {
                    String assistJSON = AssistJSON(assist.getId());

                    str.append(assistJSON);

                }
            }
        }

        return str.toString();
    }

    public List<Assist> getAll(Player j,int matchId){
        int playerId=j.getId();


        Cursor c = super.mDb.rawQuery("SELECT "+TABLE_NAME+".*"+
                        " FROM "+TABLE_NAME+" , "+DBActionDAO.TABLE_NAME+" , "+ DBPlayingTimeDAO.TABLE_NAME+
                        " WHERE " +DBActionDAO.TABLE_NAME+"."+DBActionDAO.PLAYER_ACTOR_ID_FIELD_NAME +"="+playerId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.MATCH_ID_FIELD_NAME+"="+matchId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.TEMPS_DE_JEU_FIELD_NAME
                        +" AND "+ DBAssistDAO.TABLE_NAME+"."+ DBAssistDAO.ACTION_ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.ID_FIELD_NAME
                ,
                null);
        return cursorToListAssist(c);

    }



    public String AssistJSON(int id){
        StringBuilder str=new StringBuilder("{\"TypeAction\" :{");
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        c.moveToFirst();
        int actionId=c.getInt(NUM_COL_ACTION);
        DBActionDAO dba=new DBActionDAO(mCtx);

        String actionJSON=dba.ActionJSON(actionId);
        str.append("\""+"Type:"+"\": "+"\""+"ASSIST"+"\""+" ,\n");

        for(int i=2;i<c.getColumnCount();i++) {
            String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
        str.append("},\n");
        str.append(actionJSON);
        return str.toString();
    }









    public long insert(Assist assist){
        ContentValues values = new ContentValues();
        DBActionDAO dba=new DBActionDAO(mCtx);
        dba.insert(assist);

        values.put(ACTION_ID_FIELD_NAME,dba.getLast().getActionId());
        values.put(PLAYER_TARGETED_ID_FIELD_NAME, assist.getTargetedPlayer());
        return  mDb.insert(TABLE_NAME, null, values);
    }

    public long update(int id, Assist assist){
        ContentValues values = new ContentValues();
        values.put(PLAYER_TARGETED_ID_FIELD_NAME, assist.getTargetedPlayer());


        return mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }


    public int removeWithId(int id){
        int actionId=getWithId(id).getActionId();
        mDb.delete("ACTION","ID="+actionId,null);
        return mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }


    public Assist getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToAssist(c);
    }


    public List<Assist> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                null, null, null, null, null);
        return cursorToListAssist(c);
    }


    private List<Assist> cursorToListAssist(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Assist> list = new ArrayList<Assist>();
        list.clear();

        if (c.moveToFirst()) {
            do {
                Assist assist = new Assist();
                assist.setId(c.getInt(NUM_COL_ID));
                assist.setActionId(c.getInt(NUM_COL_ACTION));
                assist.setTargetedPlayer(c.getInt(NUM_COL_PLAYER_TARGETED_ID));
                list.add(assist);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }


    private Assist cursorToAssist(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Assist assist = new Assist();

        assist.setId(c.getInt(NUM_COL_ID));
        assist.setActionId(c.getInt(NUM_COL_ACTION));
        assist.setTargetedPlayer(c.getInt(NUM_COL_PLAYER_TARGETED_ID));

        c.close();

        return assist;
    }

    public Assist getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToAssist(c);
    }







}
