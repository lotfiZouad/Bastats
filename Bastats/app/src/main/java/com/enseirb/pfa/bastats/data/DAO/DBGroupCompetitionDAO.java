package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.GroupCompetition;

import java.util.ArrayList;
import java.util.List;

public class DBGroupCompetitionDAO extends BaseDAO{

    public static final String TABLE_NAME                         = "GROUP_COMPETITION";

    private static final String ID_FIELD_NAME           		       = "_ID";
    private static final String TITLE_FIELD_NAME                       = "TITLE";
    private static final String PHASE_GROUP_COMPETITION_ID_FIELD_NAME  = "PHASE_GROUP_COMPETITION_ID";
    private static final String STATE_FIELD_NAME                       = "STATE";
    private static final String VICTORY_POINTS_FIELD_NAME              = "VICTORY_POINTS";
    private static final String DEFEAT_POINTS_FIELD_NAME               = "DEFEAT_POINTS";
    private static final String DRAW_POINTS_FIELD_NAME                 = "DRAW_POINTS";
    private static final String GOAL_AVERAGE_MAX_DIFFERENCE_FIELD_NAME = "GOAL_AVERAGE_MAX_DIFFERENCE";

    private static final String ID_FIELD_TYPE          			       = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String TITLE_FIELD_TYPE                       = "TEXT NOT NULL";
    private static final String PHASE_GROUP_COMPETITION_ID_FIELD_TYPE  = "INTEGER NOT NULL";
    private static final String STATE_FIELD_TYPE                       = "INTEGER NOT NULL";
    private static final String VICTORY_POINTS_FIELD_TYPE              = "INTEGER NOT NULL";
    private static final String DEFEAT_POINTS_FIELD_TYPE               = "INTEGER NOT NULL";
    private static final String DRAW_POINTS_FIELD_TYPE          	   = "INTEGER NOT NULL";
    private static final String GOAL_AVERAGE_MAX_DIFFERENCE_FIELD_TYPE = "INTEGER";

    private static final int NUM_COL_ID          		  		  = 0;
    private static final int NUM_COL_TITLE                        = 1;
    private static final int NUM_COL_PHASE_GROUP_COMPETITION_ID   = 2;
    private static final int NUM_COL_STATE                        = 3;
    private static final int NUM_COL_VICTORY_POINTS               = 4;
    private static final int NUM_COL_DEFEAT_POINTS                = 5;
    private static final int NUM_COL_DRAW_POINTS                  = 6;
    private static final int NUM_COL_GOAL_AVERAGE_MAX_DIFFERENCE  = 7;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
	+ ", " + TITLE_FIELD_NAME + " " + TITLE_FIELD_TYPE
	+ ", " + PHASE_GROUP_COMPETITION_ID_FIELD_NAME + " " + PHASE_GROUP_COMPETITION_ID_FIELD_TYPE
    + ", " + STATE_FIELD_NAME + " " + STATE_FIELD_TYPE
    + ", " + VICTORY_POINTS_FIELD_NAME + " " + VICTORY_POINTS_FIELD_TYPE
    + ", " + DEFEAT_POINTS_FIELD_NAME   		 + " " + DEFEAT_POINTS_FIELD_TYPE
    + ", " + DRAW_POINTS_FIELD_NAME + " " + DRAW_POINTS_FIELD_TYPE
    + ", " + GOAL_AVERAGE_MAX_DIFFERENCE_FIELD_NAME + " " + GOAL_AVERAGE_MAX_DIFFERENCE_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + PHASE_GROUP_COMPETITION_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBPhaseGroupCompetitionDAO.TABLE_NAME+"(ID)";


    public DBGroupCompetitionDAO(Context context){
        super(context);
        this.mDb = this.open();
    }

    public long insert(GroupCompetition groupCompetition){
	    ContentValues values = new ContentValues();

	    values.put(TITLE_FIELD_NAME,                groupCompetition.getTitle());
	    values.put(PHASE_GROUP_COMPETITION_ID_FIELD_NAME,         groupCompetition.getPhaseGroupCompetitionId());
        values.put(STATE_FIELD_NAME,          		  groupCompetition.getState());
        values.put(VICTORY_POINTS_FIELD_NAME,        groupCompetition.getVictoryPoints());
        values.put(DEFEAT_POINTS_FIELD_NAME,         groupCompetition.getDefeatPoints());
        values.put(DRAW_POINTS_FIELD_NAME,       	  groupCompetition.getDrawPoints());
        values.put(GOAL_AVERAGE_MAX_DIFFERENCE_FIELD_NAME, groupCompetition.getGoalAverageMaxDifference());

	    return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, GroupCompetition groupCompetition){
	    ContentValues values = new ContentValues();

	    values.put(TITLE_FIELD_NAME,                groupCompetition.getTitle());
	    values.put(PHASE_GROUP_COMPETITION_ID_FIELD_NAME,         groupCompetition.getPhaseGroupCompetitionId());
        values.put(STATE_FIELD_NAME,          		  groupCompetition.getState());
        values.put(VICTORY_POINTS_FIELD_NAME,        groupCompetition.getVictoryPoints());
        values.put(DEFEAT_POINTS_FIELD_NAME,         groupCompetition.getDefeatPoints());
        values.put(DRAW_POINTS_FIELD_NAME,       	  groupCompetition.getDrawPoints());
        values.put(GOAL_AVERAGE_MAX_DIFFERENCE_FIELD_NAME, groupCompetition.getGoalAverageMaxDifference());

        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }
 
    public int removeWithId(int id){
	    return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public GroupCompetition getWithId(int id){
	    Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
	    return cursorToGroupCompetition(c);
    }

    public List<GroupCompetition> getGroupsCompetition(int phaseGroupCompetitionId){
        Cursor c = super.mDb.query(TABLE_NAME, null, PHASE_GROUP_COMPETITION_ID_FIELD_NAME +"=" + phaseGroupCompetitionId,
                null, null, null, null);
        return cursorToListGroupCompetition(c);
    }

    public GroupCompetition getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToGroupCompetition(c);
    }



    private GroupCompetition cursorToGroupCompetition(Cursor c) {
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        GroupCompetition groupCompetition = new GroupCompetition();
        groupCompetition.setId(c.getInt(NUM_COL_ID));
        groupCompetition.setPhaseGroupCompetitionId(c.getInt(NUM_COL_PHASE_GROUP_COMPETITION_ID));
        groupCompetition.setState(c.getInt(NUM_COL_STATE));
        groupCompetition.setTitle(c.getString(NUM_COL_TITLE));
        groupCompetition.setVictoryPoints(c.getInt(NUM_COL_VICTORY_POINTS));
        groupCompetition.setDrawPoints(c.getInt(NUM_COL_DRAW_POINTS));
        groupCompetition.setDefeatPoints(c.getInt(NUM_COL_DEFEAT_POINTS));
        groupCompetition.setGoalAverageMaxDifference(c.getInt(NUM_COL_GOAL_AVERAGE_MAX_DIFFERENCE));

        c.close();

        return groupCompetition;
    }

    private List<GroupCompetition> cursorToListGroupCompetition(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<GroupCompetition> listGroupCompetitions = new ArrayList<GroupCompetition>();
        listGroupCompetitions.clear();

        if (c.moveToFirst()) {
            do {
                GroupCompetition groupCompetition = new GroupCompetition();
                groupCompetition.setId(c.getInt(NUM_COL_ID));
                groupCompetition.setPhaseGroupCompetitionId(c.getInt(NUM_COL_PHASE_GROUP_COMPETITION_ID));
                groupCompetition.setState(c.getInt(NUM_COL_STATE));
                groupCompetition.setTitle(c.getString(NUM_COL_TITLE));
                groupCompetition.setVictoryPoints(c.getInt(NUM_COL_VICTORY_POINTS));
                groupCompetition.setDrawPoints(c.getInt(NUM_COL_DRAW_POINTS));
                groupCompetition.setDefeatPoints(c.getInt(NUM_COL_DEFEAT_POINTS));
                groupCompetition.setGoalAverageMaxDifference(c.getInt(NUM_COL_GOAL_AVERAGE_MAX_DIFFERENCE));
                listGroupCompetitions.add(groupCompetition);
            } while (c.moveToNext());
        }
        c.close();
        return listGroupCompetitions;
    }
}
