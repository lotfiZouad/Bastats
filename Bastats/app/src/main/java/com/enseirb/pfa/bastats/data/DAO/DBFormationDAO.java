package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.Formation;

import java.util.ArrayList;
import java.util.List;


public class DBFormationDAO extends BaseDAO {

    public static final String TABLE_NAME         = "FORMATION";

    public static final String ID_FIELD_NAME                    = "_ID";
    public static final String TEAM_ID_FIELD_NAME               = "TEAM_ID";
    public static final String FORMATION_DEFAULT_FIELD_NAME     = "FORMATION_DEFAULT";


    private static final String ID_FIELD_TYPE                    = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String EQUIPE_ID_FIELD_TYPE             = "INTEGER";
    private static final String FORMATION_DEFAULT_FIELD_TYPE     = "INTEGER DEFAULT 0";


    private static final int NUM_COL_ID        = 0;
    private static final int NUM_COL_TEAM_ID = 1;
    private static final int NUL_COL_DEFAULT   = 2;


    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
            + ", " + TEAM_ID_FIELD_NAME + " " + EQUIPE_ID_FIELD_TYPE
            + ", " + FORMATION_DEFAULT_FIELD_NAME + " "+ FORMATION_DEFAULT_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + TEAM_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBTeamDAO.TABLE_NAME+"(ID)";



    public DBFormationDAO(Context context){
        super(context);
        super.mDb = this.open();
    }

    public long insert(int teamId){
        ContentValues values = new ContentValues();

        values.put(TEAM_ID_FIELD_NAME, teamId);
        values.put(FORMATION_DEFAULT_FIELD_NAME, Formation.FORMATION_MATCH);

        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public long insert(int teamId, int defaultValue){
        ContentValues values = new ContentValues();

        values.put(TEAM_ID_FIELD_NAME, teamId);
        values.put(FORMATION_DEFAULT_FIELD_NAME, defaultValue);

        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int removeWithId(int id){
        return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public Formation getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToFormation(c);
    }

    public Formation getDefaultFormation(int teamId){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                        TEAM_ID_FIELD_NAME +"=" + teamId
                        +" AND "+ FORMATION_DEFAULT_FIELD_NAME +"="+Formation.DEFAULT_FORMATION,
                null, null, null, null);
        if (c.getCount() == 0){
            return null;
        }
        return cursorToFormation(c);
    }

    public Formation getLastFormationUse(int teamId){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                TEAM_ID_FIELD_NAME +"=" + teamId
                        +" AND "+ FORMATION_DEFAULT_FIELD_NAME +"="+Formation.LAST,
                null, null, null, null);
        if (c.getCount() == 0){
            return null;
        }
        return cursorToFormation(c);
    }

    public List<Formation> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null , null, null, null, null, null);
        return cursorToListFormation(c);
    }

    public boolean belongTeam(int formationId, int teamId){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                TEAM_ID_FIELD_NAME +"=" + teamId
                +" AND "+ID_FIELD_NAME+"="+ formationId,
                null, null, null, null);
        return (c.getCount() != 0);
    }

    private List<Formation> cursorToListFormation(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Formation> listFormations = new ArrayList<Formation>();
        listFormations.clear();

        if (c.moveToFirst()) {
            do {
                Formation formation = new Formation();
                formation.setId(c.getInt(NUM_COL_ID));
                formation.setTeamId(c.getInt(NUM_COL_TEAM_ID));

                listFormations.add(formation);
            } while (c.moveToNext());
        }
        c.close();
        return listFormations;
    }

    private Formation cursorToFormation(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Formation formation = new Formation();
        formation.setId(c.getInt(NUM_COL_ID));
        formation.setTeamId(c.getInt(NUM_COL_TEAM_ID));
        c.close();

        return formation;
    }



    public Formation getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToFormation(c);
    }






}
