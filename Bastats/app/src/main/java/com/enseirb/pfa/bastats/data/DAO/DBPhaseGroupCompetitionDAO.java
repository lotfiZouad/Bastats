package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.PhaseGroupCompetition;

public class DBPhaseGroupCompetitionDAO extends BaseDAO{

    public static final String TABLE_NAME                   	 = "PHASE_GROUP_COMPETITION";

    private static final String ID_FIELD_NAME           		 = "_ID";
    private static final String PHASE_ID_FIELD_NAME     		 = "PHASE_ID";
    private static final String NB_GROUP_COMPETITION_FIELD_NAME  = "NB_GROUP_COMPETITION";
    private static final String NB_PERIOD_MATCH_FIELD_NAME = "NB_PERIOD_MATCH";
    private static final String DURATION_PERIOD_MATCH_FIELD_NAME = "DURATION_PERIOD_MATCH";

    private static final String ID_FIELD_TYPE          			 = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String PHASE_ID_FIELD_TYPE          	 = "INTEGER NOT NULL";
    private static final String NB_GROUP_COMPETITION_FIELD_TYPE  = "INTEGER NOT NULL";
    private static final String NB_PERIOD_MATCH_FIELD_TYPE       = "INTEGER NOT NULL";
    private static final String DURATION_PERIOD_MATCH_FIELD_TYPE = "TEXT    NOT NULL";

    private static final int NUM_COL_ID          				= 0;
    private static final int NUM_COL_PHASE         				= 1;
    private static final int NUM_COL_NB_GROUP_COMPETITION       = 2;
    private static final int NUM_COL_NB_PERIOD_MATCH            = 3;
    private static final int NUM_COL_DURATION_PERIOD_MATCH      = 4;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
	+ ", " + PHASE_ID_FIELD_NAME         		+ " " + PHASE_ID_FIELD_TYPE 
	+ ", " + NB_GROUP_COMPETITION_FIELD_NAME + " " + NB_GROUP_COMPETITION_FIELD_TYPE
    + ", " + NB_PERIOD_MATCH_FIELD_NAME + " " + NB_PERIOD_MATCH_FIELD_TYPE
    + ", " + DURATION_PERIOD_MATCH_FIELD_NAME + " " + DURATION_PERIOD_MATCH_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + PHASE_ID_FIELD_NAME+") "
            + "REFERENCES "+ DBPhaseDAO.TABLE_NAME+"(ID)";



    public DBPhaseGroupCompetitionDAO(Context context){
        super(context);
        this.mDb = this.open();
    }

    public long insert(PhaseGroupCompetition phase_groupCompetition){
	    ContentValues values = new ContentValues();

	    values.put(PHASE_ID_FIELD_NAME, phase_groupCompetition.getPhaseId());
	    values.put(NB_GROUP_COMPETITION_FIELD_NAME, phase_groupCompetition.getNbGroupCompetition());
        values.put(NB_PERIOD_MATCH_FIELD_NAME,     phase_groupCompetition.getNbPeriodMatch());
        values.put(DURATION_PERIOD_MATCH_FIELD_NAME, phase_groupCompetition.getDurationPeriodMatch());

	    return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, PhaseGroupCompetition phase_groupCompetition){
	    ContentValues values = new ContentValues();
	    values.put(PHASE_ID_FIELD_NAME, phase_groupCompetition.getPhaseId());
	    values.put(NB_GROUP_COMPETITION_FIELD_NAME, phase_groupCompetition.getNbGroupCompetition());
        values.put(NB_PERIOD_MATCH_FIELD_NAME,     phase_groupCompetition.getNbPeriodMatch());
        values.put(DURATION_PERIOD_MATCH_FIELD_NAME, phase_groupCompetition.getDurationPeriodMatch());


        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }
 
    public int removeWithId(int id){
	    return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public PhaseGroupCompetition getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToPhaseGroupCompetition(c);
    }

    public PhaseGroupCompetition getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"="+ id, null, null, null, null);
        return cursorToPhaseGroupCompetition(c);
    }

    public PhaseGroupCompetition getFromPhaseId(int phaseId){
        Cursor c = super.mDb.query(TABLE_NAME, null,PHASE_ID_FIELD_NAME +"="+ phaseId, null, null, null, null);
        return cursorToPhaseGroupCompetition(c);
    }

    private PhaseGroupCompetition cursorToPhaseGroupCompetition(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        PhaseGroupCompetition phaseGroupCompetition = new PhaseGroupCompetition();
        phaseGroupCompetition.setId(c.getInt(NUM_COL_ID));
        phaseGroupCompetition.setPhaseId(c.getInt(NUM_COL_PHASE));
        phaseGroupCompetition.setDurationPeriodMatch(c.getString(NUM_COL_DURATION_PERIOD_MATCH));
        phaseGroupCompetition.setNbPeriodMatch(c.getInt(NUM_COL_NB_PERIOD_MATCH));
        phaseGroupCompetition.setNbGroupCompetition(c.getInt(NUM_COL_NB_GROUP_COMPETITION));
        c.close();

        return phaseGroupCompetition;
    }

}