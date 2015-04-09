package com.enseirb.pfa.bastats.data.DAO;

import com.enseirb.pfa.bastats.data.model.Tournament;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class DBTournamentDAO extends BaseDAO {

    public static final String TABLE_NAME = "TOURNAMENT";

    private static final String ID_FIELD_NAME            = "_ID";
    private static final String TITLE_FIELD_NAME         = "TITLE";
    private static final String PLACE_FIELD_NAME         = "PLACE";
    private static final String NB_MAX_TEAM_FIELD_NAME   = "NB_MAX_TEAM";

    private static final String ID_FIELD_TYPE            = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String TITLE_FIELD_TYPE         = "TEXT";
    private static final String PLACE_FIELD_TYPE         = "TEXT";
    private static final String NB_MAX_TEAM_FIELD_TYPE   = "INTEGER";

    private static final int NUM_COL_ID            = 0;
    private static final int NUM_COL_TITLE         = 1;
    private static final int NUM_COL_PLACE         = 2;
    private static final int NUM_COL_NB_MAX_TEAM   = 3;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
	+ ", " + TITLE_FIELD_NAME + " " + TITLE_FIELD_TYPE
    + ", " + PLACE_FIELD_NAME + " " + PLACE_FIELD_TYPE
    + ", " + NB_MAX_TEAM_FIELD_NAME + " " + NB_MAX_TEAM_FIELD_TYPE;


    public DBTournamentDAO(Context context) {
        super(context);
        this.mDb = this.open();
    }

    public long insert(Tournament tournament) {
        ContentValues values = new ContentValues();

        values.put(TITLE_FIELD_NAME,       tournament.getTitle());
        values.put(PLACE_FIELD_NAME,          tournament.getPlace());
        values.put(NB_MAX_TEAM_FIELD_NAME, tournament.getNbTeamMax());
       
        return mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, Tournament tournament) {
        ContentValues values = new ContentValues();

        values.put(TITLE_FIELD_NAME,        tournament.getTitle());
        values.put(PLACE_FIELD_NAME,           tournament.getPlace());
        values.put(NB_MAX_TEAM_FIELD_NAME,  tournament.getNbTeamMax());
       
        return mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }


    public int removeWithId(int id) {
        return mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " + id, null);
    }


    public Tournament getWithId(int id) {
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME + "=" + id, null, null, null, null);
        return cursorToTournament(c);
    }

    public List<Tournament> getAll() {
        Cursor c = super.mDb.query(TABLE_NAME, null, null, null, null, null, null);
        return cursorToListTournament(c);
    }


    private List<Tournament> cursorToListTournament(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<Tournament> listTournaments = new ArrayList<Tournament>();
        listTournaments.clear();

        if (c.moveToFirst()) {
            do {
                Tournament tournament = new Tournament();
                tournament.setId(c.getInt(NUM_COL_ID));
                tournament.setTitle(c.getString(NUM_COL_TITLE));
                tournament.setPlace(c.getString(NUM_COL_PLACE));
                tournament.setNbTeamMax(c.getInt(NUM_COL_NB_MAX_TEAM));
              
                listTournaments.add(tournament);
            } while (c.moveToNext());
        }
        c.close();
        return listTournaments;
    }

    public Tournament getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToTournament(c);
    }

    private Tournament cursorToTournament(Cursor c) {
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Tournament tournament = new Tournament();

        tournament.setId(c.getInt(NUM_COL_ID));
        tournament.setTitle(c.getString(NUM_COL_TITLE));
        tournament.setPlace(c.getString(NUM_COL_PLACE));
        tournament.setNbTeamMax(c.getInt(NUM_COL_NB_MAX_TEAM));
     
        c.close();

        return tournament;
    }
}



