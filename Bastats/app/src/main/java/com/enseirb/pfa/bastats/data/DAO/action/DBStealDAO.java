package com.enseirb.pfa.bastats.data.DAO.action;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.DAO.BaseDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayerDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayingTimeDAO;
import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.action.Steal;

import java.util.ArrayList;
import java.util.List;

public class DBStealDAO extends BaseDAO {
    public static final String TABLE_NAME = "STEAL";

    private static final String ID_FIELD_NAME                 = "_ID";
    private static final String ACTION_ID_FIELD_NAME          = "ACTION_ID";
    private static final String PLAYER_TARGETED_ID_FIELD_NAME = "JOUEUR_CIBLE_ID";

    private static final String ID_FIELD_TYPE                 = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String ACTION_ID_FIELD_TYPE          = "INTEGER";
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

    public DBStealDAO(Context context) {
        super(context);
        this.mDb = this.open();
    }


    public String StealsJSON(int matchId, List<Player> players){
        StringBuilder str=new StringBuilder();



        for(Player j: players) {
            List<Steal> steals =getAll(j,matchId);
            if(steals !=null) {
                for (Steal steal : steals) {
                    String stealJSON = StealJSON(steal.getId());

                    str.append(stealJSON);

                }
            }
        }

        return str.toString();
    }


    public List<Steal> getAll(Player j,int matchId){
        int playerId=j.getId();


        Cursor c = super.mDb.rawQuery("SELECT "+TABLE_NAME+".*"+
                        " FROM "+TABLE_NAME+" , "+DBActionDAO.TABLE_NAME+" , "+ DBPlayingTimeDAO.TABLE_NAME+
                        " WHERE " +DBActionDAO.TABLE_NAME+"."+DBActionDAO.PLAYER_ACTOR_ID_FIELD_NAME +"="+playerId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.MATCH_ID_FIELD_NAME+"="+matchId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.TEMPS_DE_JEU_FIELD_NAME
                        +" AND "+ DBStealDAO.TABLE_NAME+"."+ DBStealDAO.ACTION_ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.ID_FIELD_NAME
                ,
                null);
        return cursorToListInterception(c);

    }






    public String StealJSON(int id){
        StringBuilder str=new StringBuilder("{\"TypeAction\" :{");
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        c.moveToFirst();
        int actionId=c.getInt(NUM_COL_ACTION);
        DBActionDAO dba=new DBActionDAO(mCtx);

        String actionJSON=dba.ActionJSON(actionId);
        str.append("\""+"Type:"+"\": "+"\""+"STEAL"+"\""+" ,\n");

        for(int i=2;i<c.getColumnCount();i++) {
            String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
        str.append("},\n");
        str.append(actionJSON);
        return str.toString();
    }








    public long insert(Steal steal){
        ContentValues values = new ContentValues();
        DBActionDAO dba=new DBActionDAO(mCtx);
        dba.insert(steal);

        values.put(ACTION_ID_FIELD_NAME,dba.getLast().getActionId());
        values.put(PLAYER_TARGETED_ID_FIELD_NAME, steal.getTargetedPlayer());
        return  mDb.insert(TABLE_NAME, null, values);
    }

    public long update(int id, Steal steal){
        ContentValues values = new ContentValues();
        values.put(PLAYER_TARGETED_ID_FIELD_NAME, steal.getTargetedPlayer());


        return mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }


    public int removeWithId(int id){
        int actionId=getWithId(id).getActionId();
        mDb.delete("ACTION","ID="+actionId,null);
        return mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }


    public Steal getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToSteal(c);
    }


    public List<Steal> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME,  null,
                null, null, null, null, null);
        return cursorToListInterception(c);
    }


    private List<Steal> cursorToListInterception(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Steal> list = new ArrayList<Steal>();
        list.clear();

        if (c.moveToFirst()) {
            do {
                Steal steal = new Steal();
                steal.setId(c.getInt(NUM_COL_ID));
                steal.setActionId(c.getInt(NUM_COL_ACTION));
                steal.setTargetedPlayer(c.getInt(NUM_COL_PLAYER_TARGETED_ID));
                list.add(steal);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }


    private Steal cursorToSteal(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Steal steal = new Steal();

        steal.setId(c.getInt(NUM_COL_ID));
        steal.setActionId(c.getInt(NUM_COL_ACTION));
        steal.setTargetedPlayer(c.getInt(NUM_COL_PLAYER_TARGETED_ID));

        c.close();

        return steal;
    }



    public Steal getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToSteal(c);
    }







}
