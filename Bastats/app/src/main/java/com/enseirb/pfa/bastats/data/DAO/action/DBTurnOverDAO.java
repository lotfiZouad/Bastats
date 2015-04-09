package com.enseirb.pfa.bastats.data.DAO.action;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.DAO.BaseDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayerDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayingTimeDAO;
import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.action.TurnOver;

import java.util.ArrayList;
import java.util.List;


public class DBTurnOverDAO extends BaseDAO {
    public static final String TABLE_NAME = "TURN_OVER";

    private static final String ID_FIELD_NAME                 = "_ID";
    private static final String ACTION_ID_FIELD_NAME          = "ACTION_ID";
    private static final String PLAYER_TARGETED_ID_FIELD_NAME = "PLAYER_TARGETED_ID";

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


    public DBTurnOverDAO(Context context) {
        super(context);
        this.mDb = this.open();
    }


    public String TurnOverJSON(int matchId, List<Player> players){
        StringBuilder str=new StringBuilder();


        for(Player j: players) {
            List<TurnOver> turnovers=getAll(j,matchId);
            if(turnovers!=null) {
                for (TurnOver turnOver : turnovers) {
                    String turnOverJson = TurnOverJSON(turnOver.getId());

                    str.append(turnOverJson);

                }
            }
        }

        return str.toString();
    }

    public List<TurnOver> getAll(Player j,int matchId){
        int joueurId=j.getId();


        Cursor c = super.mDb.rawQuery("SELECT "+TABLE_NAME+".*"+
                        " FROM "+TABLE_NAME+" , "+DBActionDAO.TABLE_NAME+" , "+ DBPlayingTimeDAO.TABLE_NAME+
                        " WHERE " +DBActionDAO.TABLE_NAME+"."+DBActionDAO.PLAYER_ACTOR_ID_FIELD_NAME +"="+joueurId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.MATCH_ID_FIELD_NAME+"="+matchId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.TEMPS_DE_JEU_FIELD_NAME
                        +" AND "+ DBTurnOverDAO.TABLE_NAME+"."+ DBTurnOverDAO.ACTION_ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.ID_FIELD_NAME
                ,
                null);
        return cursorToListTurnOver(c);

    }






    public String TurnOverJSON(int id){
        StringBuilder str=new StringBuilder("{\"TypeAction\" :{");
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        c.moveToFirst();
        int actionId=c.getInt(NUM_COL_ACTION);
        DBActionDAO dba=new DBActionDAO(mCtx);

        String actionJSON=dba.ActionJSON(actionId);
        str.append("\""+"Type:"+"\": "+"\""+"TURN_OVER"+"\""+" ,\n");

        for(int i=2;i<c.getColumnCount();i++) {
            String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
        str.append("},\n");
        str.append(actionJSON);
        return str.toString();
    }




    public long insert(TurnOver turnOver){
        ContentValues values = new ContentValues();
        DBActionDAO dba=new DBActionDAO(mCtx);
        dba.insert(turnOver);

        values.put(ACTION_ID_FIELD_NAME,dba.getLast().getActionId());
        values.put(PLAYER_TARGETED_ID_FIELD_NAME, turnOver.getTargetedPlayer());
        return  mDb.insert(TABLE_NAME, null, values);

    }

    public long update(int id, TurnOver turnOver){
        ContentValues values = new ContentValues();
        values.put(PLAYER_TARGETED_ID_FIELD_NAME, turnOver.getTargetedPlayer());
        return mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }


    public int removeWithId(int id){
        int actionId=getWithId(id).getActionId();
        mDb.delete("ACTION","ID="+actionId,null);
        return mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }


    public TurnOver getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToTurnOver(c);
    }


    public List<TurnOver> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                null, null, null, null, null);
        return cursorToListTurnOver(c);
    }


    private List<TurnOver> cursorToListTurnOver(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<TurnOver> list = new ArrayList<TurnOver>();
        list.clear();

        if (c.moveToFirst()) {
            do {
                TurnOver turnOver = new TurnOver();
                turnOver.setId(c.getInt(NUM_COL_ID));
                turnOver.setActionId(c.getInt(NUM_COL_ACTION));
                turnOver.setTargetedPlayer(c.getInt(NUM_COL_PLAYER_TARGETED_ID));
                list.add(turnOver);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }


    private TurnOver cursorToTurnOver(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        TurnOver turnOver = new TurnOver();

        turnOver.setId(c.getInt(NUM_COL_ID));
        turnOver.setActionId(c.getInt(NUM_COL_ACTION));
        turnOver.setTargetedPlayer(c.getInt(NUM_COL_PLAYER_TARGETED_ID));

        c.close();

        return turnOver;
    }


    public TurnOver getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToTurnOver(c);
    }







}
