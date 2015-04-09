package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.Phase;

import java.util.ArrayList;
import java.util.List;

public class DBPhaseDAO extends BaseDAO{

    public static final String TABLE_NAME                 = "PHASE";

    private static final String ID_FIELD_NAME          	  = "_ID";
    private static final String TOURNAMENT_ID_FIELD_NAME  = "TOURNAMENT_ID";
    private static final String TITLE_FIELD_NAME          = "TITLE";
    private static final String DATE_FIELD_NAME           = "DATE";
    private static final String TEMPORAL_ORDER_FIELD_NAME = "TEMPORAL_ORDER";
    private static final String TYPE_FIELD_NAME           = "TYPE";
    

    private static final String ID_FIELD_TYPE          	  = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String TOURNAMENT_ID_FIELD_TYPE  = "INTEGER NOT NULL";
    private static final String TITLE_FIELD_TYPE          = "TEXT    NOT NULL";
    private static final String DATE_FIELD_TYPE        	  = "TEXT";
    private static final String TEMPORAL_ORDER_FIELD_TYPE = "INTEGER NOT NULL";
    private static final String TYPE_FIELD_TYPE           = "INTEGER NOT NULL";


    private static final int NUM_COL_ID          		  = 0;
    private static final int NUM_COL_TOURNAMENT_ID        = 1;
    private static final int NUM_COL_TITLE                = 2;
    private static final int NUM_COL_DATE      			  = 3;
    private static final int NUM_COL_TEMPORAL_ORDER       = 4;
    private static final int NUM_COL_TYPE   			  = 5;


    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
	+ ", " + TOURNAMENT_ID_FIELD_NAME + " " + TOURNAMENT_ID_FIELD_TYPE
	+ ", " + TITLE_FIELD_NAME + " " + TITLE_FIELD_TYPE
    + ", " + DATE_FIELD_NAME      			+ " " + DATE_FIELD_TYPE
    + ", " + TEMPORAL_ORDER_FIELD_NAME + " " + TEMPORAL_ORDER_FIELD_TYPE
    + ", " + TYPE_FIELD_NAME   				+ " " + TYPE_FIELD_TYPE
    + ", " + "FOREIGN KEY (" + TOURNAMENT_ID_FIELD_NAME +") "
    + "REFERENCES "+ DBTournamentDAO.TABLE_NAME+"(ID)";



    public DBPhaseDAO(Context context){
        super(context);
        this.mDb = this.open();
    }

    public long insert(Phase phase){
	    ContentValues values = new ContentValues();

	    values.put(TOURNAMENT_ID_FIELD_NAME,       phase.getTournamentId());
	    values.put(TITLE_FIELD_NAME,          phase.getTitle());
        values.put(DATE_FIELD_NAME,          	phase.getDate());
        values.put(TEMPORAL_ORDER_FIELD_NAME,   phase.getTemporalOrder());
        values.put(TYPE_FIELD_NAME,       		phase.getType());


	    return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, Phase phase){
	    ContentValues values = new ContentValues();
	    values.put(TOURNAMENT_ID_FIELD_NAME,       phase.getTournamentId());
	    values.put(TITLE_FIELD_NAME,          phase.getTitle());
        values.put(DATE_FIELD_NAME,          	phase.getDate());
        values.put(TEMPORAL_ORDER_FIELD_NAME,   phase.getTemporalOrder());
        values.put(TYPE_FIELD_NAME,       		phase.getType());


        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }
 
    public int removeWithId(int id){
	    return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }
    
    public List<Phase> getAll() {
        Cursor c = super.mDb.query(TABLE_NAME, null, null, null, null, null, null);
        return cursorToListPhase(c);
    }

    public Phase getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"="+ id, null, null, null, null);
        return cursorToPhase(c);
    }

    public List<Phase> getTournamentPhases(int tournamentId){
        Cursor c = super.mDb.query(TABLE_NAME, null, TOURNAMENT_ID_FIELD_NAME +"="+tournamentId, null, null, null, null);
        if (c.getCount() == 0)
            return null;
        else return cursorToListPhase(c);
    }

    private List<Phase> cursorToListPhase(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<Phase> listPhases = new ArrayList<Phase>();
        listPhases.clear();

        if (c.moveToFirst()) {
            do {
                Phase phase = new Phase();
                phase.setId(c.getInt(NUM_COL_ID));
                phase.setTournamentId(c.getInt(NUM_COL_TOURNAMENT_ID));
                phase.setTitle(c.getString(NUM_COL_TITLE));
                phase.setType(c.getInt(NUM_COL_TYPE));
                phase.setTemporalOrder(c.getInt(NUM_COL_TEMPORAL_ORDER));
                phase.setDate(c.getString(NUM_COL_DATE));

                listPhases.add(phase);
            } while (c.moveToNext());
        }
        c.close();
        return listPhases;
    }

    public Phase getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToPhase(c);
    }

    private Phase cursorToPhase(Cursor c) {
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Phase phase = new Phase();
        phase.setId(c.getInt(NUM_COL_ID));
        phase.setTournamentId(c.getInt(NUM_COL_TOURNAMENT_ID));
        phase.setTitle(c.getString(NUM_COL_TITLE));
        phase.setType(c.getInt(NUM_COL_TYPE));
        phase.setTemporalOrder(c.getInt(NUM_COL_TEMPORAL_ORDER));
        phase.setDate(c.getString(NUM_COL_DATE));

        c.close();

        return phase;
    }

}
