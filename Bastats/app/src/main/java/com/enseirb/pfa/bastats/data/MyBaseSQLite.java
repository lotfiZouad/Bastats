package com.enseirb.pfa.bastats.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enseirb.pfa.bastats.data.DAO.DBGroupCompetitionDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPhaseGroupCompetitionDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPhaseResultsTableDAO;
import com.enseirb.pfa.bastats.data.DAO.DBResultsTableTeamDAO;
import com.enseirb.pfa.bastats.data.DAO.DBTournamentRankingDAO;
import com.enseirb.pfa.bastats.data.DAO.DBFormationDAO;
import com.enseirb.pfa.bastats.data.DAO.DBFormationPlayerDAO;
import com.enseirb.pfa.bastats.data.DAO.DBGroupCompetitionMatchDAO;
import com.enseirb.pfa.bastats.data.DAO.DBGroupCompetitionTeamDAO;
import com.enseirb.pfa.bastats.data.DAO.DBMatchDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPhaseDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayerDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayingTimeDAO;
import com.enseirb.pfa.bastats.data.DAO.DBTeamDAO;
import com.enseirb.pfa.bastats.data.DAO.DBTournamentDAO;
import com.enseirb.pfa.bastats.data.DAO.DBTournamentTeamDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBActionDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBAssistDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBBlockDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBFaultDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBReboundDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBStealDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBTurnOverDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBShootDAO;

public class MyBaseSQLite extends SQLiteOpenHelper {

    public MyBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Model
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBPlayerDAO.TABLE_NAME + " ("
                + DBPlayerDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBPlayingTimeDAO.TABLE_NAME + " ("
                + DBPlayingTimeDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBTeamDAO.TABLE_NAME + " ("
                + DBTeamDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBActionDAO.TABLE_NAME + " ("
                + DBActionDAO.CREATE_TABLE_STATEMENT + ")");
        // Actions
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBShootDAO.TABLE_NAME + " ("
                + DBShootDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBReboundDAO.TABLE_NAME + " ("
                + DBReboundDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBAssistDAO.TABLE_NAME + " ("
                + DBAssistDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBBlockDAO.TABLE_NAME + " ("
                + DBBlockDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBStealDAO.TABLE_NAME + " ("
                + DBStealDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBFaultDAO.TABLE_NAME + " ("
                + DBFaultDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBTurnOverDAO.TABLE_NAME + " ("
                + DBTurnOverDAO.CREATE_TABLE_STATEMENT + ")");

        // Match
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBMatchDAO.TABLE_NAME + " ("
                + DBMatchDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBFormationDAO.TABLE_NAME + " ("
                + DBFormationDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBFormationPlayerDAO.TABLE_NAME + " ("
                + DBFormationPlayerDAO.CREATE_TABLE_STATEMENT + ")");

        // Tournament
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBTournamentDAO.TABLE_NAME + " ("
                + DBTournamentDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBTournamentRankingDAO.TABLE_NAME + " ("
                + DBTournamentRankingDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBTournamentTeamDAO.TABLE_NAME + " ("
                + DBTournamentTeamDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBResultsTableTeamDAO.TABLE_NAME + " ("
                + DBResultsTableTeamDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBPhaseGroupCompetitionDAO.TABLE_NAME + " ("
                + DBPhaseGroupCompetitionDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBPhaseDAO.TABLE_NAME + " ("
                + DBPhaseDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBPhaseResultsTableDAO.TABLE_NAME + " ("
                + DBPhaseResultsTableDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBGroupCompetitionTeamDAO.TABLE_NAME + " ("
                + DBGroupCompetitionTeamDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBGroupCompetitionMatchDAO.TABLE_NAME + " ("
                + DBGroupCompetitionMatchDAO.CREATE_TABLE_STATEMENT + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBGroupCompetitionDAO.TABLE_NAME + " ("
                + DBGroupCompetitionDAO.CREATE_TABLE_STATEMENT + ")");

        db.execSQL("PRAGMA foreign_keys=ON;"); //allows to delete on cascade (foreign key)

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Model
        db.execSQL("DROP TABLE " + DBPlayerDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DBPlayingTimeDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DBTeamDAO.TABLE_NAME + ";");
        // Actions
        db.execSQL("DROP TABLE " + DBShootDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DBReboundDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DBAssistDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DBBlockDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DBStealDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DBFaultDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DBTurnOverDAO.TABLE_NAME + ";");
        onCreate(db);



    }

}

