package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.GroupCompetitionMatch;

import java.util.ArrayList;
import java.util.List;


public class DBGroupCompetitionMatchDAO extends  BaseDAO{

    public static final String TABLE_NAME = "GROUP_COMPETITION_MATCH";

    public static final String GROUP_COMPETITION_ID_FIELD_NAME = "GROUP_COMPETITION_ID";
    public static final String MATCH_ID_FIELD_NAME = "MATCH_ID";

    private static final String GROUP_COMPETITION_ID_FIELD_TYPE = "INTEGER";
    private static final String MATCH_ID_FIELD_TYPE             = "INTEGER";

    private static final int NUM_COL_GROUP_COMPETITION_ID  = 0;
    private static final int NUM_COL_MATCH_ID              = 1;

    public static final String CREATE_TABLE_STATEMENT = GROUP_COMPETITION_ID_FIELD_NAME + " " + GROUP_COMPETITION_ID_FIELD_TYPE
            + ", " + MATCH_ID_FIELD_NAME + " " + MATCH_ID_FIELD_TYPE
            + ", " + "PRIMARY KEY(" + GROUP_COMPETITION_ID_FIELD_NAME + ", " + MATCH_ID_FIELD_NAME + ")"
            + ", " + "FOREIGN KEY (" + GROUP_COMPETITION_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBGroupCompetitionDAO.TABLE_NAME + "(ID)"
            + ", " + "FOREIGN KEY (" + MATCH_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBMatchDAO.TABLE_NAME + "(ID)";



    public DBGroupCompetitionMatchDAO(Context context) {
        super(context);
        super.mDb = this.open();
    }

    public long insert(int groupCompetitionId, int matchId){
        ContentValues values = new ContentValues();

        values.put(GROUP_COMPETITION_ID_FIELD_NAME, groupCompetitionId);
        values.put(MATCH_ID_FIELD_NAME, matchId);
        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int removeWithId(int groupCompetitionId,int matchId){
        return super.mDb.delete(TABLE_NAME, GROUP_COMPETITION_ID_FIELD_NAME + " = " + groupCompetitionId+" AND "+MATCH_ID_FIELD_NAME+ " = " + matchId, null);
    }


    public GroupCompetitionMatch getWithId(int groupCompetitionId,int matchId){
        Cursor c = super.mDb.query(TABLE_NAME, null, GROUP_COMPETITION_ID_FIELD_NAME + " = " + groupCompetitionId+" AND "+MATCH_ID_FIELD_NAME+ " = " + matchId, null, null, null, null);
        return cursorToGroupCompetitionMatch(c);
    }


    public List<Integer> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null, null, null, null, null, null);
        return cursorToListGroupCompetitionMatch(c);
    }

    public List<Integer> getMatchIdsFromGroupCompetition(int groupCompetitionId){
        Cursor c = super.mDb.rawQuery("SELECT "+MATCH_ID_FIELD_NAME+
                " FROM "+TABLE_NAME+
                " WHERE "+TABLE_NAME+"."+ GROUP_COMPETITION_ID_FIELD_NAME +"="+groupCompetitionId, null);
        return cursorToListGroupCompetitionMatch(c);
    }

    public List<Integer> cursorToListGroupCompetitionMatch(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Integer> matchList = new ArrayList<>();
        matchList.clear();

        if (c.moveToFirst()) {
            do {
                matchList.add(c.getInt(0));
            } while (c.moveToNext());
        }
        c.close();
        return matchList;
    }


    private GroupCompetitionMatch cursorToGroupCompetitionMatch(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        GroupCompetitionMatch groupCompetitionMatch = new GroupCompetitionMatch();
        groupCompetitionMatch.setGroupCompetitionId(c.getInt(NUM_COL_GROUP_COMPETITION_ID));
        groupCompetitionMatch.setMatchId(c.getInt(NUM_COL_MATCH_ID));

        c.close();

        return groupCompetitionMatch;
    }




}
