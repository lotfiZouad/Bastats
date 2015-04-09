package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.FormationPlayer;
import com.enseirb.pfa.bastats.data.model.Player;

import java.util.ArrayList;
import java.util.List;

public class DBFormationPlayerDAO extends  BaseDAO {

    public static final String TABLE_NAME = "FORMATION_PLAYER";

    public static final String FORMATION_ID_FIELD_NAME = "FORMATION_ID";
    public static final String PLAYER_ID_FIELD_NAME    = "PLAYER_ID";
    public static final String NUMBER_FIELD_NAME       = "NUMBER";

    private static final String FORMATION_ID_FIELD_TYPE = "INTEGER";
    private static final String PLAYER_ID_FIELD_TYPE    = "INTEGER";
    private static final String NUMBER_FIELD_TYPE       = "TEXT";

    private static final int NUM_COL_FORMATION_ID = 0;
    private static final int NUM_COL_PLAYER_ID    = 1;
    private static final int NUM_COL_NUMBER       = 2;

    public static final String CREATE_TABLE_STATEMENT = FORMATION_ID_FIELD_NAME + " " + FORMATION_ID_FIELD_TYPE
            + ", " + PLAYER_ID_FIELD_NAME + " " + PLAYER_ID_FIELD_TYPE
            + ", " + NUMBER_FIELD_NAME + " " + NUMBER_FIELD_TYPE
            + ", " + "PRIMARY KEY(" + FORMATION_ID_FIELD_NAME + ", " + PLAYER_ID_FIELD_NAME + ")"
            + ", " + "FOREIGN KEY (" + FORMATION_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBFormationDAO.TABLE_NAME + "(ID)"
            + ", " + "FOREIGN KEY (" + PLAYER_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBPlayerDAO.TABLE_NAME + "(ID)";


    public DBFormationPlayerDAO(Context context) {
        super(context);
        super.mDb = this.open();
    }


    public String FormationsPlayerJSON(int formationId, List<Player> players){
        StringBuilder str=new StringBuilder();

        for(Player j: players) {


                    String formationJson = FormationPlayerJSON(formationId, j.getId());

                    str.append(formationJson);

                }
        return str.toString();
        }







    public String FormationPlayerJSON(int formationId, int playerId){
        StringBuilder str=new StringBuilder("{\"formationPlayer\" :{");
        Cursor c = super.mDb.query(TABLE_NAME, null,
                FORMATION_ID_FIELD_NAME +" = "+formationId+" AND "+ PLAYER_ID_FIELD_NAME +" = "+playerId
                , null, null, null, null);
        c.moveToFirst();



        for(int i=0;i<c.getColumnCount();i++) {
            String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
        str.append("}\n}\n");

        return str.toString();
    }



    public long insert(FormationPlayer formationPlayer){
        ContentValues values = new ContentValues();

        values.put(FORMATION_ID_FIELD_NAME, formationPlayer.getFormationId());
        values.put(PLAYER_ID_FIELD_NAME, formationPlayer.getPlayerId());
        values.put(NUMBER_FIELD_NAME, formationPlayer.getNumber());
        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public long insert(int formationId, int playerId){
        ContentValues values = new ContentValues();

        values.put(FORMATION_ID_FIELD_NAME,formationId);
        values.put(PLAYER_ID_FIELD_NAME,playerId);
        values.put(NUMBER_FIELD_NAME,"");
        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int formationId,int playerId, String number){
        ContentValues values = new ContentValues();
        values.put(NUMBER_FIELD_NAME, number);


        return super.mDb.update(TABLE_NAME, values, FORMATION_ID_FIELD_NAME + " = " + formationId+" AND "+ PLAYER_ID_FIELD_NAME + " = " + playerId, null);
    }


    public int removeWithId(int formationId,int playerId){
        return super.mDb.delete(TABLE_NAME, FORMATION_ID_FIELD_NAME + " = " + formationId+" AND "+ PLAYER_ID_FIELD_NAME + " = " + playerId, null);
    }

    public FormationPlayer getWithId(int formationId,int playerId){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                FORMATION_ID_FIELD_NAME +" = "+formationId+" AND "+ PLAYER_ID_FIELD_NAME +" = "+playerId
                , null, null, null, null);
        return cursorToFormationPlayer(c);
    }

    public List<FormationPlayer> getWithId(int formationId){
        Cursor c = super.mDb.query(TABLE_NAME, null,FORMATION_ID_FIELD_NAME + " = " + formationId,
                null, null, null, null);
        return cursorToListFormationPlayer(c);
    }


    public List<FormationPlayer> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null, null, null, null, null, null);
        return cursorToListFormationPlayer(c);
    }


    private List<FormationPlayer> cursorToListFormationPlayer(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<FormationPlayer> listFormationPlayers = new ArrayList<FormationPlayer>();
        listFormationPlayers.clear();

        if (c.moveToFirst()) {
            do {
                FormationPlayer formationPlayer = new FormationPlayer();
                formationPlayer.setFormationId(c.getInt(NUM_COL_FORMATION_ID));
                formationPlayer.setPlayerId(c.getInt(NUM_COL_PLAYER_ID));
                formationPlayer.setNumber(c.getString(NUM_COL_NUMBER));
                listFormationPlayers.add(formationPlayer);
            } while (c.moveToNext());
        }
        c.close();
        return listFormationPlayers;
    }


    private FormationPlayer cursorToFormationPlayer(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        FormationPlayer formationPlayer = new FormationPlayer();
        formationPlayer.setFormationId(c.getInt(NUM_COL_FORMATION_ID));
        formationPlayer.setPlayerId(c.getInt(NUM_COL_PLAYER_ID));
        formationPlayer.setNumber(c.getString(NUM_COL_NUMBER));


        c.close();

        return formationPlayer;
    }








}














