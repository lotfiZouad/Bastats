package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.PhaseResultsTable;

public class DBPhaseResultsTableDAO extends BaseDAO{

    public static final String TABLE_NAME                   = "PHASE_RESULTS_TABLE";

    private static final String ID_FIELD_NAME           	= "_ID";
    private static final String PHASE_ID_FIELD_NAME         = "PHASE_ID";
    private static final String STATE_FIELD_NAME            = "STATE";

    private static final String ID_FIELD_TYPE          		= "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String PHASE_ID_FIELD_TYPE         = "INTEGER NOT NULL";
    private static final String STATE_FIELD_TYPE            = "INTEGER NOT NULL";

    private static final int NUM_COL_ID          			= 0;
    private static final int NUM_COL_PHASE_ID         		= 1;
    private static final int NUM_COL_STATE                  = 2;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
	+ ", " + PHASE_ID_FIELD_NAME         + " " + PHASE_ID_FIELD_TYPE 
	+ ", " + STATE_FIELD_NAME + " " + STATE_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + PHASE_ID_FIELD_NAME+") "
            + "REFERENCES "+ DBPhaseDAO.TABLE_NAME+"(ID)";


	public DBPhaseResultsTableDAO(Context context){
        super(context);
        this.mDb = this.open();
    }

    public long insert(PhaseResultsTable phaseResultsTable){
	    ContentValues values = new ContentValues();

	    values.put(PHASE_ID_FIELD_NAME,             phaseResultsTable.getPhaseId());
	    values.put(STATE_FIELD_NAME,          phaseResultsTable.getState());

	    return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, PhaseResultsTable phaseResultsTable){
	    ContentValues values = new ContentValues();
	    values.put(PHASE_ID_FIELD_NAME,             phaseResultsTable.getPhaseId());
	    values.put(STATE_FIELD_NAME,          phaseResultsTable.getState());


        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }
 
    public int removeWithId(int id){
	    return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public PhaseResultsTable getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"="+ id, null, null, null, null);
        return cursorToPhaseTableau(c);
    }

    public PhaseResultsTable getFromPhaseId(int phaseId){
        Cursor c = super.mDb.query(TABLE_NAME, null,PHASE_ID_FIELD_NAME +"="+ phaseId, null, null, null, null);
        return cursorToPhaseTableau(c);
    }

    public PhaseResultsTable getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToPhaseTableau(c);
    }

    private PhaseResultsTable cursorToPhaseTableau(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        PhaseResultsTable phaseResultsTable = new PhaseResultsTable();
        phaseResultsTable.setId(c.getInt(NUM_COL_ID));
        phaseResultsTable.setPhaseId(c.getInt(NUM_COL_PHASE_ID));
        phaseResultsTable.setState(c.getInt(NUM_COL_STATE));
        c.close();

        return phaseResultsTable;
    }

}