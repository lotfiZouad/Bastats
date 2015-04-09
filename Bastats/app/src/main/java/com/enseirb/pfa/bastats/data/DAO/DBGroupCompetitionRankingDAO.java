package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;

import com.enseirb.pfa.bastats.data.model.GroupCompetitionRanking;


public class DBGroupCompetitionRankingDAO extends BaseDAO   {

    public static final String TABLE_NAME = "GROUP_COMPETITION_RANKING";

    private static final String GROUP_COMPETITION_ID_FIELD_NAME = "GROUP_COMPETITION_ID";
    private static final String TEAM_ID_FIELD_NAME  = "TEAM_ID";
    private static final String POINTS_FIELD_NAME   = "POINTS";

    private static final String GROUP_COMPETITION_ID_FIELD_TYPE = "INTEGER";
    private static final String TEAM_ID_FIELD_TYPE = "INTEGER";
    private static final String POINTS_FIELD_TYPE = "INTEGER";

    private static final int NUM_COL_GROUP_COMPETITION_ID = 0;
    private static final int NUM_COL_TEAM_ID = 1;
    private static final int NUM_COL_POINTS = 2;


    public static final String CREATE_TABLE_STATEMENT = GROUP_COMPETITION_ID_FIELD_NAME + " " + GROUP_COMPETITION_ID_FIELD_TYPE
            + ", " + TEAM_ID_FIELD_NAME + " " + TEAM_ID_FIELD_TYPE
            + ", " + POINTS_FIELD_NAME + " " + POINTS_FIELD_TYPE
            + ", " + "PRIMARY KEY(" + GROUP_COMPETITION_ID_FIELD_NAME + ", " + TEAM_ID_FIELD_NAME + ")"
            + ", " + "FOREIGN KEY (" + GROUP_COMPETITION_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBGroupCompetitionDAO.TABLE_NAME + "(ID)"
            + ", " + "FOREIGN KEY (" + TEAM_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBTeamDAO.TABLE_NAME + "(ID)";



    public DBGroupCompetitionRankingDAO(Context context) {
        super(context);
        super.mDb = this.open();
    }


    public long insert(GroupCompetitionRanking groupCompetitionRanking){
        ContentValues values = new ContentValues();

        values.put(GROUP_COMPETITION_ID_FIELD_NAME, groupCompetitionRanking.getGroupCompetitionId());
        values.put(TEAM_ID_FIELD_NAME, groupCompetitionRanking.getTeamId());
        values.put(POINTS_FIELD_NAME, groupCompetitionRanking.getPoints());
        return super.mDb.insert(TABLE_NAME, null, values);
    }


    public int update(int teamId,int groupCompetitionId,int points){
        ContentValues values = new ContentValues();
        values.put(POINTS_FIELD_NAME, points);


        return super.mDb.update(TABLE_NAME, values, GROUP_COMPETITION_ID_FIELD_NAME + " = " + groupCompetitionId+" AND "+ TEAM_ID_FIELD_NAME + " = " + teamId, null);
    }

    public int removeWithId(int teamId,int groupCompetitionId){
        return super.mDb.delete(TABLE_NAME, GROUP_COMPETITION_ID_FIELD_NAME + " = " + groupCompetitionId+" AND "+ TEAM_ID_FIELD_NAME + " = " + teamId, null);
    }




}
