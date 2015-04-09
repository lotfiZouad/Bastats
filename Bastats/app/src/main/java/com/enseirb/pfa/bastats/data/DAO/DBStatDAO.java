package com.enseirb.pfa.bastats.data.DAO;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.enseirb.pfa.bastats.data.DAO.action.*;
import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.Stat;
import com.enseirb.pfa.bastats.data.model.action.Rebound;
import com.enseirb.pfa.bastats.data.model.action.Shoot;


public class DBStatDAO extends BaseDAO {
    private static final String TAG = "Stats";

    public DBStatDAO(Context context) {
        super(context);
        this.mDb = this.open();
    }

    public Stat getStatFromPlayer(Player player, int matchId) {
        Stat stat = new Stat(player.getId());
        stat.setPlayerName(player.getSurname());


        int nbPasseDecisive = getNbAssist(stat.getIdJoueur(), matchId);
        stat.setNbAssist(nbPasseDecisive);


        int nbFaultOff = getNbFaultOff(stat.getIdJoueur(), matchId);
        stat.setNbFaultOff(nbFaultOff);


        int nbFaultDef = getNbFaultDef(stat.getIdJoueur(), matchId);
        stat.setNbFaultDef(nbFaultDef);


        int nbBlock = getNbBlock(stat.getIdJoueur(), matchId);
        stat.setNbBlock(nbBlock);
        Log.d(TAG, "nbBlock: "+nbBlock);

        int nbReboundOff = getNbReboundOff(stat.getIdJoueur(), matchId);
        stat.setNbReboundOff(nbReboundOff);
        Log.d(TAG, "nbReboundOff: "+nbReboundOff);

        int nbReboundDef = getNbReboundDef(stat.getIdJoueur(), matchId);
        stat.setNbReboundDef(nbReboundDef);
        Log.d(TAG, "nbReboundDef: "+nbReboundDef);

        int nbSteal = getNbSteal(stat.getIdJoueur(), matchId);
        stat.setNbSteal(nbSteal);
        Log.d(TAG, "nbSteal: "+nbSteal);

        int nbTurnOver = getNbTurnOver(stat.getIdJoueur(), matchId);
        stat.setNbTurnOver(nbTurnOver);
        Log.d(TAG, "nbTurnOver: "+nbTurnOver);

        int nbFtS = getNbFtS(stat.getIdJoueur(), matchId);
        stat.setNbFtS(nbFtS);


        int nbFtF = getNbFtF(stat.getIdJoueur(), matchId);
        stat.setNbFtF(nbFtF);


        int nbShoot2S = getNbShoot2S(stat.getIdJoueur(), matchId);
        stat.setNbShoot2S(nbShoot2S);
        Log.d(TAG, "nbShoot2S: "+nbShoot2S);

        int nbShoot2F = getNbShoot2F(stat.getIdJoueur(), matchId);
        stat.setNbShoot2F(nbShoot2F);
        Log.d(TAG, "nbShoot2F: "+nbShoot2F);

        int nbShoot3S = getNbShoot3S(stat.getIdJoueur(), matchId);
        stat.setNbShoot3S(nbShoot3S);
        Log.d(TAG, "nbShoot3S: "+nbShoot3S);

        int nbShoot3F = getNbShoot3F(stat.getIdJoueur(), matchId);
        stat.setNbShoot3F(nbShoot3F);
        Log.d(TAG, "nbShoot3F: "+nbShoot3F);

        return stat;
    }

    public int getNbAssist(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBAssistDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbFaultOff(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBFaultDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbFaultDef(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBFaultDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                //TODO " AND TYPE.TYPE = 1" +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbBlock(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBBlockDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbReboundOff(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBReboundDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND TYPE.TYPE = " + Rebound.OFFENSIVE +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbReboundDef(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBReboundDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND TYPE.TYPE = " + Rebound.DEFENSIVE +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbSteal(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBStealDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbTurnOver(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBTurnOverDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " + Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND ACTION.PLAYER_ACTOR_ID = " + Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbFtS(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBShootDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND TYPE.PTS = 1" +
                " AND TYPE.SUCCESSFUL = "+ Shoot.SUCCESSFUL+
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbFtF(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBShootDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND TYPE.PTS = 1" +
                " AND TYPE.SUCCESSFUL = "+ Shoot.FAILED +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbShoot2S(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                " FROM "+ DBShootDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                " AND ACTION._ID = TYPE.ACTION_ID" +
                " AND TYPE.PTS = 2" +
                " AND TYPE.SUCCESSFUL = "+ Shoot.SUCCESSFUL+
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbShoot2F(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                        " FROM "+ DBShootDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                        " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                        " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                        " AND ACTION._ID = TYPE.ACTION_ID" +
                        " AND TYPE.PTS = 2" +
                        " AND TYPE.SUCCESSFUL = " + Shoot.FAILED +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbShoot3S(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                        " FROM "+ DBShootDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                        " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                        " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                        " AND ACTION._ID = TYPE.ACTION_ID" +
                        " AND TYPE.PTS = 3" +
                        " AND TYPE.SUCCESSFUL = "+Shoot.SUCCESSFUL+
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }

    public int getNbShoot3F(int playerId, int matchId) {
        Cursor c = mDb.rawQuery("SELECT COUNT(*)" +
                        " FROM "+ DBShootDAO.TABLE_NAME + " AS TYPE, ACTION, PLAYING_TIME" +
                        " WHERE PLAYING_TIME.MATCH_ID = " +Integer.toString(matchId)+
                        " AND PLAYING_TIME._ID = ACTION.PLAYING_TIME_ID" +
                        " AND ACTION._ID = TYPE.ACTION_ID" +
                        " AND TYPE.PTS = 3" +
                        " AND TYPE.SUCCESSFUL = "+Shoot.FAILED +
                " AND ACTION.PLAYER_ACTOR_ID = " +Integer.toString(playerId),null);
        return cursorToInteger(c);
    }



    public Cursor getHistory(int matchId) {
        Cursor c = mDb.rawQuery("SELECT ACTION._id, ACTION.joueur_acteur_id, TEMPS_DE_JEU.temps_restant " +
                "FROM" + DBPlayingTimeDAO.TABLE_NAME+", "+DBMatchDAO.TABLE_NAME+", "+DBActionDAO.TABLE_NAME+
                "TEMPS_DE_JEU, MATCH, ACTION" +
                "WHERE" +
                "MATCH._id "+matchId+" AND "+
                "TEMPS_DE_JEU.match_id = MATCH._id AND " +
                "ACTION.temps_de_jeu_id = TEMPS_DE_JEU._id", null);
        return c;
    }

    public int cursorToInteger(Cursor c) {
        if (c.getCount() == 0) {
            return 0;
        }
        c.moveToFirst();
        int res = c.getInt(0);
        c.close();
        return res;
    }

}
