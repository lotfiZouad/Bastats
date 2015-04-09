package com.enseirb.pfa.bastats.data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.PlayingTime;

import java.util.ArrayList;
import java.util.List;

public class DBPlayingTimeDAO extends BaseDAO{

    public static final String TABLE_NAME                   = "PLAYING_TIME";

    public static final String  ID_FIELD_NAME                = "_ID";
    public static final String  MATCH_ID_FIELD_NAME          = "MATCH_ID";
    private static final String NUMBER_QT_FIELD_NAME         = "NUMBER_QT";
    private static final String CHRONOMETER_FIELD_NAME       = "CHRONOMETER";
    private static final String NB_FAULT_A_FIELD_NAME        = "NB_FAULT_A";
    private static final String NB_FAULT_B_FIELD_NAME        = "NB_FAULT_B";
    private static final String TITLE_FIELD_NAME             = "TITLE";

    private static final String ID_FIELD_TYPE               = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String MATCH_ID_FIELD_TYPE         = "INTEGER";
    private static final String NUMBER_QT_FIELD_TYPE        = "INTEGER";
    private static final String CHRONOMETER_FIELD_TYPE      = "TEXT";
    private static final String NB_FAULT_A_FIELD_TYPE       = "INTEGER";
    private static final String NB_FAULT_B_FIELD_TYPE       = "INTEGER";
    private static final String TITLE_FIELD_TYPE            = "TEXT";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_MATCHID = 1;
    private static final int NUM_COL_NUMBER_QT = 2;
    private static final int NUM_COL_CHRONOMETER = 3;
    private static final int NUM_COL_FAULT_A = 4;
    private static final int NUM_COL_FAULT_B = 5;
    private static final int NUM_COL_TITLE   = 6;

    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
            + ", " + MATCH_ID_FIELD_NAME     + " " + MATCH_ID_FIELD_TYPE
            + ", " + NUMBER_QT_FIELD_NAME + " " + NUMBER_QT_FIELD_TYPE
            + ", " + CHRONOMETER_FIELD_NAME + " " + CHRONOMETER_FIELD_TYPE
            + ", " + NB_FAULT_A_FIELD_NAME + " " + NB_FAULT_A_FIELD_TYPE
            + ", " + NB_FAULT_B_FIELD_NAME + " " + NB_FAULT_B_FIELD_TYPE
            + ", " + TITLE_FIELD_NAME + " " + TITLE_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + MATCH_ID_FIELD_NAME+") "
            + "REFERENCES "+ DBMatchDAO.TABLE_NAME+"(ID)";

    public DBPlayingTimeDAO(Context context){
        super(context);
        super.mDb = this.open();
    }

    public long insert(PlayingTime playingTime){
        ContentValues values = new ContentValues();

        values.put(MATCH_ID_FIELD_NAME, playingTime.getMatchId());
        values.put(NUMBER_QT_FIELD_NAME, playingTime.getNumberQT());
        values.put(CHRONOMETER_FIELD_NAME, playingTime.getChronometer());
        values.put(NB_FAULT_A_FIELD_NAME, playingTime.getNbFaultTeamA());
        values.put(NB_FAULT_B_FIELD_NAME, playingTime.getNbFaultTeamB());
        values.put(TITLE_FIELD_NAME, playingTime.getTitle());

        return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, PlayingTime playingTime){
        ContentValues values = new ContentValues();

        values.put(MATCH_ID_FIELD_NAME, playingTime.getMatchId());
        values.put(NUMBER_QT_FIELD_NAME, playingTime.getNumberQT());
        values.put(CHRONOMETER_FIELD_NAME, playingTime.getChronometer());
        values.put(NB_FAULT_A_FIELD_NAME, playingTime.getNbFaultTeamA());
        values.put(NB_FAULT_B_FIELD_NAME, playingTime.getNbFaultTeamB());
        values.put(TITLE_FIELD_NAME, playingTime.getTitle());

        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }

    public String PlayingTimeJSON(int id){
        StringBuilder str=new StringBuilder("\"playingTime\" :{");
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        c.moveToFirst();
        for(int i=1;i<c.getColumnCount();i++) {
            String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
        str.append("}\n}");
        return str.toString();
    }






    public int removeWithId(int id){
        return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public PlayingTime getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, new String[] {ID_FIELD_NAME, MATCH_ID_FIELD_NAME,
                        NUMBER_QT_FIELD_NAME, CHRONOMETER_FIELD_NAME, NB_FAULT_A_FIELD_NAME,
                        NB_FAULT_B_FIELD_NAME, TITLE_FIELD_NAME},
                ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToPlayingTime(c);
    }

    public List<PlayingTime> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, new String[] {ID_FIELD_NAME, MATCH_ID_FIELD_NAME,
                NUMBER_QT_FIELD_NAME, CHRONOMETER_FIELD_NAME, NB_FAULT_A_FIELD_NAME,
                NB_FAULT_B_FIELD_NAME, TITLE_FIELD_NAME} , null, null, null, null, null);
        return cursorToListTime(c);
    }

    private List<PlayingTime> cursorToListTime(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<PlayingTime> listTime = new ArrayList<PlayingTime>();
        listTime.clear();

        if (c.moveToFirst()) {
            do {
                PlayingTime playingTime = new PlayingTime();
                playingTime.setId(c.getInt(NUM_COL_ID));
                playingTime.setMatchId(c.getInt(NUM_COL_MATCHID));
                playingTime.setNumberQT(c.getInt(NUM_COL_NUMBER_QT));
                playingTime.setChronometer(c.getString(NUM_COL_CHRONOMETER));
                playingTime.setNbFaultTeamA(c.getInt(NUM_COL_FAULT_A));
                playingTime.setNbFaultTeamB(c.getInt(NUM_COL_FAULT_B));
                playingTime.setTitle(c.getString(NUM_COL_TITLE));
                listTime.add(playingTime);
            } while (c.moveToNext());
        }
        c.close();
        return listTime;
    }

    private PlayingTime cursorToPlayingTime(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();

        PlayingTime playingTime = new PlayingTime();
        playingTime.setId(c.getInt(NUM_COL_ID));
        playingTime.setMatchId(c.getInt(NUM_COL_MATCHID));
        playingTime.setNumberQT(c.getInt(NUM_COL_NUMBER_QT));
        playingTime.setChronometer(c.getString(NUM_COL_CHRONOMETER));
        playingTime.setNbFaultTeamA(c.getInt(NUM_COL_FAULT_A));
        playingTime.setNbFaultTeamB(c.getInt(NUM_COL_FAULT_B));
        playingTime.setTitle(c.getString(NUM_COL_TITLE));

        c.close();

        return playingTime;
    }

    public PlayingTime getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToPlayingTime(c);
    }


}
