package com.enseirb.pfa.bastats.data.DAO;

import com.enseirb.pfa.bastats.data.model.Team;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class DBTeamDAO extends BaseDAO {

    public static final String TABLE_NAME = "TEAM";

    private static final String ID_FIELD_NAME           = "_ID";
    private static final String COLOR_BASE_FIELD_NAME   = "COLOR_BASE";
    private static final String PHOTO_FIELD_NAME        = "PHOTO";
    private static final String NAME_FIELD_NAME         = "NOM";
    private static final String ACRONYM_FIELD_NAME      = "ACRONYM";

    private static final String ID_FIELD_TYPE           = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COLOR_BASE_FIELD_TYPE   = "TEXT";
    private static final String PHOTO_FIELD_TYPE        = "TEXT";
    private static final String NAME_FIELD_TYPE         = "TEXT";
    private static final String ACRONYM_FIELD_TYPE      = "TEXT";

    private static final int NUM_COL_ID                 = 0;
    private static final int NUM_COL_COLOR_BASE         = 1;
    private static final int NUM_COL_PHOTO              = 2;
    private static final int NUM_COL_NAME               = 3;
    private static final int NUM_COL_ACRONYM            = 4;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
            + ", " + COLOR_BASE_FIELD_NAME + " " + COLOR_BASE_FIELD_TYPE
            + ", " + PHOTO_FIELD_NAME + " " + PHOTO_FIELD_TYPE
            + ", " + NAME_FIELD_NAME + " " + NAME_FIELD_TYPE
            + ", " + ACRONYM_FIELD_NAME + " " + ACRONYM_FIELD_TYPE;


    public DBTeamDAO(Context context) {
        super(context);
        this.mDb = this.open();
    }

    public long insert(Team team) {
        ContentValues values = new ContentValues();

        values.put(COLOR_BASE_FIELD_NAME, team.getColor());
        values.put(PHOTO_FIELD_NAME, team.getPhoto());
        values.put(NAME_FIELD_NAME, team.getName());
        values.put(ACRONYM_FIELD_NAME, team.getAcronym());

        return mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, Team team) {
        ContentValues values = new ContentValues();
        values.put(COLOR_BASE_FIELD_NAME, team.getColor());
        values.put(PHOTO_FIELD_NAME, team.getPhoto());
        values.put(NAME_FIELD_NAME, team.getName());
        values.put(ACRONYM_FIELD_NAME, team.getAcronym());

        return mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);

    }


    public int removeWithId(int id) {
        return mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " + id, null);
    }


    public Team getWithId(int id) {
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME + "=" + id, null, null, null, null);
        return cursorToTeam(c);
    }

    public int getTeamId(String name){
        Cursor c = mDb.rawQuery("SELECT "+ID_FIELD_NAME+
                                " FROM "+TABLE_NAME+
                                " WHERE "+TABLE_NAME+"."+ NAME_FIELD_NAME +"= ?", new String[] {name});
        int id = -1;
        if (c.moveToFirst())
            id = c.getInt(NUM_COL_ID);
        c.close();

        return id;
    }



    public List<Team> getAll() {
        Cursor c = super.mDb.query(TABLE_NAME, null, null, null, null, null, null);
        return cursorToListTeam(c);
    }

    public Team getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToTeam(c);
    }

    private List<Team> cursorToListTeam(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<Team> listTeams = new ArrayList<Team>();
        listTeams.clear();

        if (c.moveToFirst()) {
            do {
                Team team = new Team();
                team.setId(c.getInt(NUM_COL_ID));
                team.setColor(c.getString(NUM_COL_COLOR_BASE));
                team.setPhoto(c.getString(NUM_COL_PHOTO));
                team.setName(c.getString(NUM_COL_NAME));
                team.setAcronym(c.getString(NUM_COL_ACRONYM));
                listTeams.add(team);
            } while (c.moveToNext());
        }
        c.close();
        return listTeams;
    }


    private Team cursorToTeam(Cursor c) {
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Team team = new Team();

        team.setId(c.getInt(NUM_COL_ID));
        team.setColor(c.getString(NUM_COL_COLOR_BASE));
        team.setPhoto(c.getString(NUM_COL_PHOTO));
        team.setName(c.getString(NUM_COL_NAME));
        team.setAcronym(c.getString(NUM_COL_ACRONYM));

        c.close();

        return team;
    }
}



