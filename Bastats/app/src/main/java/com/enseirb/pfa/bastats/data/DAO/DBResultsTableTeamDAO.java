package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;


public class DBResultsTableTeamDAO extends BaseDAO{
    public static final String TABLE_NAME = "RESULTS_TABLE_TEAM";

    private static final String ID_RESULTS_TABLE_FIELD_NAME = "ID_RESULTS_TABLE";
    private static final String ID_TEAM_FIELD_NAME          = "ID_TEAM";

    private static final String ID_RESULTS_TABLE_FIELD_TYPE = "INTEGER NOT NULL";
    private static final String ID_TEAM_FIELD_TYPE          = "INTEGER NOT NULL";

    private static final int NUM_COL_ID_RESULTS_TABLE       = 0;
    private static final int NUM_COL_ID_TEAM                = 1;


    public static final String CREATE_TABLE_STATEMENT = ID_RESULTS_TABLE_FIELD_NAME + " " + ID_RESULTS_TABLE_FIELD_TYPE
            + "," + ID_TEAM_FIELD_NAME + " " + ID_TEAM_FIELD_TYPE
            + ", " + "PRIMARY KEY(" + ID_RESULTS_TABLE_FIELD_NAME + ", " + ID_TEAM_FIELD_NAME + ")"
            + ", " + "FOREIGN KEY (" + ID_RESULTS_TABLE_FIELD_NAME +") "
            + "REFERENCES "+ DBPhaseResultsTableDAO.TABLE_NAME+"(ID)"
            + ", " + "FOREIGN KEY (" + ID_TEAM_FIELD_NAME +") "
            + "REFERENCES "+ DBTeamDAO.TABLE_NAME+"(ID) ";


    public DBResultsTableTeamDAO(Context context) {
        super(context);
        this.mDb = this.open();
    }

    public long insert(int resultTablesId, int teamId) {
        ContentValues values = new ContentValues();

        values.put(ID_RESULTS_TABLE_FIELD_NAME, resultTablesId);
        values.put(ID_TEAM_FIELD_NAME, teamId);

        return mDb.insert(TABLE_NAME, null, values);
    }

    public List<Integer> getTeamsList(int resultTablesId){
        Cursor c = super.mDb.rawQuery("SELECT "+ ID_TEAM_FIELD_NAME +
                " FROM "+TABLE_NAME+
                " WHERE "+TABLE_NAME+"."+ ID_RESULTS_TABLE_FIELD_NAME +"="+resultTablesId, null);

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
