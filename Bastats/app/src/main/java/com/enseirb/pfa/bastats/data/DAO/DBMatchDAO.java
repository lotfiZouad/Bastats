package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.DAO.action.DBAssistDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBBlockDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBFaultDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBReboundDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBStealDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBTurnOverDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBShootDAO;
import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.Match;

import java.util.ArrayList;
import java.util.List;


public class DBMatchDAO extends BaseDAO {

    public static final String TABLE_NAME = "MATCH";

    private static final String ID_FIELD_NAME              = "_ID";
    private static final String TITLE_FIELD_NAME           = "TITLE";
    private static final String DATE_FIELD_NAME            = "DATE";
    private static final String FORMATION1_ID_FIELD_NAME   = "FORMATION1_ID";
    private static final String FORMATION2_ID_FIELD_NAME   = "FORMATION2_ID";
    private static final String RULE_FORMATION1_FIELD_NAME = "RULE_FORMATION1";
    private static final String RULE_FORMATION2_FIELD_NAME = "RULE_FORMATION2";
    private static final String RESULT_FIELD_NAME          = "RESULT";
    private static final String SCORE_TEAM_1_FIELD_NAME    = "SCORE1";
    private static final String SCORE_TEAM_2_FIELD_NAME    = "SCORE2";
    private static final String PHASE_ID_FIELD_NAME        = "PHASE_ID";
    private static final String REFEREE1_FIELD_NAME        = "REFEREE1";
    private static final String REFEREE2_FIELD_NAME        = "REFEREE2";

    private static final String ID_FIELD_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String TITLE_FIELD_TYPE ="TEXT";
    private static final String DATE_FIELD_TYPE="TEXT";
    private static final String FORMATION1_ID_FIELD_TYPE="INTEGER";
    private static final String FORMATION2_ID_FIELD_TYPE="INTEGER";
    private static final String RULE_FORMATION1_FIELD_TYPE ="TEXT";
    private static final String RULE_FORMATION2_FIELD_TYPE ="TEXT";
    private static final String RESULT_FIELD_TYPE ="INTEGER";
    private static final String SCORE_TEAM_1_FIELD_TYPE ="INTEGER";
    private static final String SCORE_TEAM_2_FIELD_TYPE ="INTEGER";
    private static final String PHASE_ID_FIELD_TYPE="INTEGER";
    private static final String REFEREE1_FIELD_TYPE ="TEXT";
    private static final String REFEREE2_FIELD_TYPE ="TEXT";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_TITLE = 1;
    private static final int NUM_COL_DATE = 2;
    private static final int NUM_COL_FORMATION1 = 3;
    private static final int NUM_COL_FORMATION2 = 4;
    private static final int NUM_COL_RULE_FORMATION1 = 5;
    private static final int NUM_COL_RULE_FORMATION2 = 6;
    private static final int NUM_COL_RESULT = 7;
    private static final int NUM_COL_SCORE_TEAM_1 = 8;
    private static final int NUM_COL_SCORE_TEAM_2 = 9;
    private static final int NUM_COL_PHASE_ID = 10;
    private static final int NUM_COL_REFEREE1 = 11;
    private static final int NUM_COL_REFEREE2 = 12;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
            + ", " + TITLE_FIELD_NAME + " " + TITLE_FIELD_TYPE
            + ", " + DATE_FIELD_NAME + " " + DATE_FIELD_TYPE
            + ", " + FORMATION1_ID_FIELD_NAME + " " + FORMATION1_ID_FIELD_TYPE
            + ", " + FORMATION2_ID_FIELD_NAME + " " + FORMATION2_ID_FIELD_TYPE
            + ", " + RULE_FORMATION1_FIELD_NAME + " " + RULE_FORMATION1_FIELD_TYPE
            + ", " + RULE_FORMATION2_FIELD_NAME + " " + RULE_FORMATION2_FIELD_TYPE
            + ", " + RESULT_FIELD_NAME + " " + RESULT_FIELD_TYPE
            + ", " + SCORE_TEAM_1_FIELD_NAME + " " + SCORE_TEAM_1_FIELD_TYPE
            + ", " + SCORE_TEAM_2_FIELD_NAME + " " + SCORE_TEAM_2_FIELD_TYPE
            + ", " + PHASE_ID_FIELD_NAME + " " + PHASE_ID_FIELD_TYPE
            + ", " + REFEREE1_FIELD_NAME + " " + REFEREE1_FIELD_TYPE
            + ", " + REFEREE2_FIELD_NAME + " " + REFEREE2_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + FORMATION1_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBFormationDAO.TABLE_NAME + "(ID)"
            + ", " + "FOREIGN KEY (" + FORMATION2_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBFormationDAO.TABLE_NAME + "(ID)"
            + ", " + "FOREIGN KEY (" + PHASE_ID_FIELD_NAME + ") "
            + "REFERENCES " + DBPhaseDAO.TABLE_NAME + "(ID)";

    public DBMatchDAO(Context context) {
        super(context);
        super.mDb = this.open();
    }

    public long insert(Match match){
        ContentValues values = new ContentValues();

        values.put(TITLE_FIELD_NAME,match.getTitle());
        values.put(DATE_FIELD_NAME,match.getDate());
        values.put(FORMATION1_ID_FIELD_NAME,match.getFormationTeamA());
        values.put(FORMATION2_ID_FIELD_NAME,match.getFormationTeamB());
        values.put(RULE_FORMATION1_FIELD_NAME,match.getRuleFormationA());
        values.put(RULE_FORMATION2_FIELD_NAME,match.getRuleFormationB());
        values.put(RESULT_FIELD_NAME,match.getResult());
        values.put(SCORE_TEAM_1_FIELD_NAME,match.getScoreTeamA());
        values.put(SCORE_TEAM_2_FIELD_NAME,match.getScoreTeamB());
        values.put(PHASE_ID_FIELD_NAME,match.getPhaseId());
        values.put(REFEREE1_FIELD_NAME, match.getRefereeField());
        values.put(REFEREE2_FIELD_NAME, match.getRefereeAssistant());


        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, Match match){
        ContentValues values = new ContentValues();
        values.put(TITLE_FIELD_NAME,match.getTitle());
        values.put(DATE_FIELD_NAME,match.getDate());
        values.put(FORMATION1_ID_FIELD_NAME,match.getFormationTeamA());
        values.put(FORMATION2_ID_FIELD_NAME,match.getFormationTeamB());
        values.put(RULE_FORMATION1_FIELD_NAME,match.getRuleFormationA());
        values.put(RULE_FORMATION2_FIELD_NAME,match.getRuleFormationB());
        values.put(RESULT_FIELD_NAME,match.getResult());
        values.put(SCORE_TEAM_1_FIELD_NAME,match.getScoreTeamA());
        values.put(SCORE_TEAM_2_FIELD_NAME,match.getScoreTeamB());
        values.put(PHASE_ID_FIELD_NAME,match.getPhaseId());
        values.put(REFEREE1_FIELD_NAME, match.getRefereeField());
        values.put(REFEREE2_FIELD_NAME, match.getRefereeAssistant());

        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }

    public String MatchJSON(int id,List<Player> playersA,List<Player> playersB,int formationA,int formationB){
        StringBuilder str=new StringBuilder("{\n \"match\":{\n");
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        c.moveToFirst();
        for(int i=0;i<c.getColumnCount();i++) {
          String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
            str.append("\"TeamA\":\n{\n\"actions\": [\n");


        DBShootDAO dbs       =new DBShootDAO(mCtx);
        DBReboundDAO dbr      =new DBReboundDAO(mCtx);
        DBTurnOverDAO dbpe =new DBTurnOverDAO(mCtx);
        DBAssistDAO dbp       =new DBAssistDAO(mCtx);
        DBStealDAO dbi=new DBStealDAO(mCtx);
        DBFaultDAO dbf =new DBFaultDAO(mCtx);
        DBBlockDAO dbc =new DBBlockDAO(mCtx);
        DBFormationPlayerDAO dbfj =new DBFormationPlayerDAO(mCtx);

        str.append(dbs.ShootsJSON(id,playersA));
        str.append(dbr.ReboundsJSON(id, playersA));
        str.append(dbpe.TurnOverJSON(id, playersA));
        str.append(dbp.AssistsJSON(id, playersA));
        str.append(dbi.StealsJSON(id, playersA));
        str.append(dbf.FaultsJSON(id, playersA));
        str.append(dbc.BlocksJSON(id, playersA));

        str.append("]\n\"formation\":[");
        str.append(dbfj.FormationsPlayerJSON(formationA, playersA));

        str.append("]\n} ,\n \"teamB\":\n{\"actions\":[\n");

        str.append(dbs.ShootsJSON(id,playersB));
        str.append(dbr.ReboundsJSON(id, playersB));
        str.append(dbpe.TurnOverJSON(id, playersB));
        str.append(dbp.AssistsJSON(id, playersA));
        str.append(dbi.StealsJSON(id, playersB));
        str.append(dbf.FaultsJSON(id, playersB));
        str.append(dbc.BlocksJSON(id, playersB));

        str.append("]\n\"formation\":[");
        str.append(dbfj.FormationsPlayerJSON(formationB, playersB));

        str.append("]\n}\n}\n}");


        return str.toString();
    }

    public int removeWithId(int id){
        return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public Match getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToMatch(c);
    }

    public List<Match> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null, null, null, null, null, null);
        return cursorToListMatch(c);
    }

    public Match getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToMatch(c);
    }

    public List<Match> getMatchGroupCompetition(int groupCompetitionId){
        Cursor c = super.mDb.rawQuery("SELECT "+TABLE_NAME+".*"+
                " FROM "+TABLE_NAME+" , "+ DBGroupCompetitionMatchDAO.TABLE_NAME+
                " WHERE "+ DBGroupCompetitionMatchDAO.TABLE_NAME+"."+ DBGroupCompetitionMatchDAO.GROUP_COMPETITION_ID_FIELD_NAME +"="+groupCompetitionId
                +" AND "+ DBGroupCompetitionMatchDAO.TABLE_NAME+"."+ DBGroupCompetitionMatchDAO.MATCH_ID_FIELD_NAME+"="+ID_FIELD_NAME,
                null);
        return cursorToListMatch(c);
    }

    public List<Match> getMatchgroupCompetitionTeam(int groupCompetitionId, int teamId){
        String pm = DBGroupCompetitionMatchDAO.TABLE_NAME;
        String formation = DBFormationDAO.TABLE_NAME;
        String rawQuery = " SELECT "+TABLE_NAME+".*"
                +" FROM "+TABLE_NAME+" , "+pm
                +" WHERE "+TABLE_NAME+"."+ID_FIELD_NAME+"="+pm+"."+ DBGroupCompetitionMatchDAO.MATCH_ID_FIELD_NAME
                +" AND "+pm+"."+ DBGroupCompetitionMatchDAO.GROUP_COMPETITION_ID_FIELD_NAME +"="+groupCompetitionId
                +" AND ("+TABLE_NAME+"."+FORMATION1_ID_FIELD_NAME+" IN "
                    +" ( SELECT "+formation+"."+DBFormationDAO.ID_FIELD_NAME
                    +" FROM "+formation
                    +" WHERE "+formation+"."+DBFormationDAO.TEAM_ID_FIELD_NAME +"="+teamId+")"
                    +" OR "
                    +TABLE_NAME+"."+FORMATION2_ID_FIELD_NAME+" IN "
                    +" ( SELECT "+formation+"."+DBFormationDAO.ID_FIELD_NAME
                    +" FROM "+formation
                    +" WHERE "+formation+"."+DBFormationDAO.TEAM_ID_FIELD_NAME +"="+teamId+"))";


        Cursor c = super.mDb.rawQuery(rawQuery,
                null);
        return cursorToListMatch(c);
    }

    public List<Match> getFromPhaseType(int phaseId){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                PHASE_ID_FIELD_NAME + "=" + phaseId, null, null, null, null);
        return cursorToListMatch(c);
    }

    private List<Match> cursorToListMatch(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Match> listMatchs = new ArrayList<Match>();
        listMatchs.clear();

        if (c.moveToFirst()) {
            do {
                Match match = new Match();
                match.setId(c.getInt(NUM_COL_ID));
                match.setTitle(c.getString(NUM_COL_TITLE));
                match.setDate(c.getString(NUM_COL_DATE));
                match.setFormationTeamA(c.getInt(NUM_COL_FORMATION1));
                match.setFormationTeamB(c.getInt(NUM_COL_FORMATION2));
                match.setRuleFormationA(c.getString(NUM_COL_RULE_FORMATION1));
                match.setRuleFormationB(c.getString(NUM_COL_RULE_FORMATION2));
                match.setResult(c.getInt(NUM_COL_RESULT));
                match.setScoreTeamA(c.getInt(NUM_COL_SCORE_TEAM_1));
                match.setScoreTeamB(c.getInt(NUM_COL_SCORE_TEAM_2));
                match.setPhaseId(c.getInt(NUM_COL_PHASE_ID));
                match.setRefereeField(c.getString(NUM_COL_REFEREE1));
                match.setRefereeAssistant(c.getString(NUM_COL_REFEREE2));
                listMatchs.add(match);
            } while (c.moveToNext());
        }
        c.close();
        return listMatchs;
    }

    private Match cursorToMatch(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Match match = new Match();
        match.setId(c.getInt(NUM_COL_ID));
        match.setTitle(c.getString(NUM_COL_TITLE));
        match.setDate(c.getString(NUM_COL_DATE));
        match.setFormationTeamA(c.getInt(NUM_COL_FORMATION1));
        match.setFormationTeamB(c.getInt(NUM_COL_FORMATION2));
        match.setRuleFormationA(c.getString(NUM_COL_RULE_FORMATION1));
        match.setRuleFormationB(c.getString(NUM_COL_RULE_FORMATION2));
        match.setResult(c.getInt(NUM_COL_RESULT));
        match.setScoreTeamA(c.getInt(NUM_COL_SCORE_TEAM_1));
        match.setScoreTeamB(c.getInt(NUM_COL_SCORE_TEAM_2));
        match.setPhaseId(c.getInt(NUM_COL_PHASE_ID));
        match.setRefereeField(c.getString(NUM_COL_REFEREE1));
        match.setRefereeAssistant(c.getString(NUM_COL_REFEREE2));

        c.close();

        return match;
    }

}
