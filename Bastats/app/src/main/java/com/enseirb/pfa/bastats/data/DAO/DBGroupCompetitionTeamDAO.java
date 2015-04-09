package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DBGroupCompetitionTeamDAO extends BaseDAO{

    public static final String TABLE_NAME                   = "GROUP_COMPETITION_TEAM";

    public static final String GROUP_COMPETITION_ID_FIELD_NAME = "GROUP_COMPETITION_ID";
    public static final String TEAM_ID_FIELD_NAME               = "TEAM_ID";


    private static final String ID_FIELD_TYPE          		= "INTEGER NOT NULL";
    private static final String TEAM_ID_FIELD_TYPE          = "INTEGER NOT NULL";


    private static final int NUM_COL_GROUP_COMPETITION_ID   = 0;
    private static final int NUM_COL_TEAM_ID                = 1;


    public static final String CREATE_TABLE_STATEMENT = GROUP_COMPETITION_ID_FIELD_NAME + " " + ID_FIELD_TYPE
	+ ", " + TEAM_ID_FIELD_NAME + " " + TEAM_ID_FIELD_TYPE

            + ", " + "PRIMARY KEY(" + GROUP_COMPETITION_ID_FIELD_NAME + ", " + TEAM_ID_FIELD_NAME + ")"
            + ", " + "FOREIGN KEY (" + GROUP_COMPETITION_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBGroupCompetitionDAO.TABLE_NAME+"(ID)"
            + ", " + "FOREIGN KEY (" + TEAM_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBTeamDAO.TABLE_NAME+"(ID) ";

	public DBGroupCompetitionTeamDAO(Context context){
        super(context);
        this.mDb = this.open();
    }

    public long insert(int groupCompetitionId, int teamId){
	    ContentValues values = new ContentValues();

        values.put(GROUP_COMPETITION_ID_FIELD_NAME, groupCompetitionId);
	    values.put(TEAM_ID_FIELD_NAME, teamId);


	    return super.mDb.insert(TABLE_NAME, null, values);
    }

    public List<Integer> getTeamsList(int groupCompetitionId){
        Cursor c = super.mDb.rawQuery("SELECT "+ TEAM_ID_FIELD_NAME +
                " FROM "+TABLE_NAME+
                " WHERE "+TABLE_NAME+"."+ GROUP_COMPETITION_ID_FIELD_NAME +"="+groupCompetitionId, null);

        return cursorToTeamList(c);
    }

    public List<Integer> cursorToTeamList(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Integer> teamList = new ArrayList<>();
        teamList.clear();

        if (c.moveToFirst()) {
            do {
                teamList.add(c.getInt(0));
            } while (c.moveToNext());
        }
        c.close();
        return teamList;
    }
}