package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.TournamentRanking;

import java.util.ArrayList;
import java.util.List;


public class DBTournamentRankingDAO extends BaseDAO {

    public static final String TABLE_NAME                         = "TOURNAMENT_RANKING";

    private static final String ID_FIELD_NAME           		  = "_ID";
    private static final String RULE_TEAM_FIELD_NAME              = "RULE_TEAM";
    private static final String TEAM_ID_FIELD_NAME                = "TEAM_ID";
    private static final String POINTS_FIELD_NAME                 = "POINTS";
    private static final String PLACE_FIELD_NAME                  = "PLACE";
    private static final String TOURNAMENT_ID_FIELD_NAME          = "TOURNAMENT_ID";

    private static final String ID_FIELD_TYPE          			  = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String RULE_TEAM_FIELD_TYPE              = "TEXT";
    private static final String TEAM_ID_FIELD_TYPE                = "INTEGER NOT NULL";
    private static final String POINTS_FIELD_TYPE                 = "INTEGER NOT NULL";
    private static final String PLACE_FIELD_TYPE                  = "INTEGER NOT NULL";
    private static final String TOURNAMENT_ID_FIELD_TYPE          = "INTEGER NOT NULL";

    private static final int NUM_COL_ID          		  	  = 0;
    private static final int NUM_COL_RULE_TEAM                = 1;
    private static final int NUM_COL_TEAM_ID                  = 2;
    private static final int NUM_COL_POINTS       		      = 3;
    private static final int NUM_COL_PLACE   			      = 4;
    private static final int NUM_COL_TOURNAMENT_ID            = 5;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
	+ ", " + RULE_TEAM_FIELD_NAME + " " + RULE_TEAM_FIELD_TYPE
	+ ", " + TEAM_ID_FIELD_NAME + " " + TEAM_ID_FIELD_TYPE
	+ ", " + POINTS_FIELD_NAME      	 + " " + POINTS_FIELD_TYPE
	+ ", " + PLACE_FIELD_NAME        	 + " " + PLACE_FIELD_TYPE
	+ ", " + TOURNAMENT_ID_FIELD_NAME + " " + TOURNAMENT_ID_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + TOURNAMENT_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBTournamentDAO.TABLE_NAME+"(ID)"
            + ", " + "FOREIGN KEY (" + TEAM_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBTeamDAO.TABLE_NAME+"(ID) ";



    public DBTournamentRankingDAO(Context context){
        super(context);
        this.mDb = this.open();
    }

    public long insert(TournamentRanking tr){
        ContentValues values = new ContentValues();

        values.put(RULE_TEAM_FIELD_NAME, tr.getRuleTeam());
        values.put(TEAM_ID_FIELD_NAME, tr.getTeamId());
        values.put(POINTS_FIELD_NAME,        tr.getPoints());
        values.put(PLACE_FIELD_NAME, tr.getPlace());
        values.put(TOURNAMENT_ID_FIELD_NAME,       	  tr.getTournamentId());

        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, TournamentRanking tr){
        ContentValues values = new ContentValues();

        values.put(RULE_TEAM_FIELD_NAME, tr.getRuleTeam());
        values.put(TEAM_ID_FIELD_NAME, tr.getTeamId());
        values.put(POINTS_FIELD_NAME,        tr.getPoints());
        values.put(PLACE_FIELD_NAME, tr.getPlace());
        values.put(TOURNAMENT_ID_FIELD_NAME,       	  tr.getTournamentId());
        
        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }

    public int removeWithId(int id){
        return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public TournamentRanking getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToRankingTournament(c);
    }

    public TournamentRanking getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToRankingTournament(c);
    }

    public List<Integer> getTeamsList(int tr){
        Cursor c = super.mDb.rawQuery("SELECT "+ TEAM_ID_FIELD_NAME +
                " FROM "+TABLE_NAME+
                " WHERE "+TABLE_NAME+"."+ID_FIELD_NAME+"="+tr, null);

        return cursorToTeamList(c);
    }

    public List<TournamentRanking> getRanking(int tournamentId){
        Cursor c = super.mDb.query(TABLE_NAME, null, TOURNAMENT_ID_FIELD_NAME +"=" + tournamentId,
                null, null, null, null);
        return cursorToListTournamentRanking(c);
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

    private TournamentRanking cursorToRankingTournament(Cursor c) {
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        TournamentRanking clt = new TournamentRanking();
        clt.setId(c.getInt(NUM_COL_ID));
        clt.setTeamId(c.getInt(NUM_COL_TEAM_ID));
        clt.setRuleTeam(c.getString(NUM_COL_RULE_TEAM));
        clt.setPoints(c.getInt(NUM_COL_POINTS));
        clt.setTournamentId(c.getInt(NUM_COL_TOURNAMENT_ID));
        clt.setPlace(c.getInt(NUM_COL_PLACE));

        c.close();

        return clt;
    }

    private List<TournamentRanking> cursorToListTournamentRanking(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<TournamentRanking> listTournamentRankings = new ArrayList<TournamentRanking>();
        listTournamentRankings.clear();

        if (c.moveToFirst()) {
            do {
                TournamentRanking clt = new TournamentRanking();
                clt.setId(c.getInt(NUM_COL_ID));
                clt.setTeamId(c.getInt(NUM_COL_TEAM_ID));
                clt.setRuleTeam(c.getString(NUM_COL_RULE_TEAM));
                clt.setPoints(c.getInt(NUM_COL_POINTS));
                clt.setTournamentId(c.getInt(NUM_COL_TOURNAMENT_ID));
                clt.setPlace(c.getInt(NUM_COL_PLACE));
                listTournamentRankings.add(clt);
            } while (c.moveToNext());
        }
        c.close();
        return listTournamentRankings;
    }
}
