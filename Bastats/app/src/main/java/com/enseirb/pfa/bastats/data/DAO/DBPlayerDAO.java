package com.enseirb.pfa.bastats.data.DAO;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.model.Formation;
import com.enseirb.pfa.bastats.data.model.Player;

import java.util.ArrayList;
import java.util.List;

public class DBPlayerDAO extends BaseDAO {

    public  static final String TABLE_NAME             = "PLAYER";

    private static final String ID_FIELD_NAME          = "_ID";
    private static final String SURNAME_FIELD_NAME     = "SURNAME";
    private static final String FIRST_NAME_FIELD_NAME  = "FIRST_NAME";
    private static final String PSEUDO_FIELD_NAME      = "PSEUDO";
    private static final String NUMBER_FIELD_NAME      = "NUMBER";

    private static final String ID_FIELD_TYPE          = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String SURNAME_TYPE           = "TEXT";
    private static final String FIRST_NAME_FIELD_TYPE  = "TEXT";
    private static final String PSEUDO_FIELD_TYPE      = "TEXT";
    private static final String NUMBER_FIELD_TYPE      = "TEXT";

    private static final int NUM_COL_ID          = 0;
    private static final int NUM_COL_SURNAME     = 1;
    private static final int NUM_COL_FIRST_NAME  = 2;
    private static final int NUM_COL_PSEUDO      = 3;
    private static final int NUM_COL_NUMBER      = 4;


    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
	+ ", " + SURNAME_FIELD_NAME + " " + SURNAME_TYPE
	+ ", " + FIRST_NAME_FIELD_NAME + " " + FIRST_NAME_FIELD_TYPE
    + ", " + PSEUDO_FIELD_NAME      + " " + PSEUDO_FIELD_TYPE
    + ", " + NUMBER_FIELD_NAME + " " + NUMBER_FIELD_TYPE;

    public DBPlayerDAO(Context context){
        super(context);
        this.mDb = this.open();
    }

    public long insert(Player player){
	    ContentValues values = new ContentValues();

	    values.put(SURNAME_FIELD_NAME,             player.getSurname());
	    values.put(FIRST_NAME_FIELD_NAME,          player.getFirstName());
        values.put(PSEUDO_FIELD_NAME,          player.getPseudo());
        values.put(NUMBER_FIELD_NAME,          player.getNumber());

	    return super.mDb.insert(TABLE_NAME, null, values);
    }

    public int update(int id, Player player){
	    ContentValues values = new ContentValues();
	    values.put(SURNAME_FIELD_NAME,             player.getSurname());
	    values.put(FIRST_NAME_FIELD_NAME,          player.getFirstName());
        values.put(PSEUDO_FIELD_NAME,          player.getPseudo());
        values.put(NUMBER_FIELD_NAME,          player.getNumber());


        return super.mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }
 
    public int removeWithId(int id){
	    return super.mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }

    public Player getWithId(int id){
	    Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
	    return cursorToPlayer(c);
    }

    public List<Player> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null, null, null, null, null, null);
        return cursorToListPlayer(c);
    }

    public Player getLast(){
        Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
        c.moveToLast();
        return cursorToPlayer(c);
    }

    public List<Player> getPlayersTeam(int teamId) {
        String fj = DBFormationPlayerDAO.TABLE_NAME;
        String f = DBFormationDAO.TABLE_NAME;
        String rawQuery = " SELECT *"
                +" FROM "+TABLE_NAME
                +" WHERE "+TABLE_NAME+"."+ID_FIELD_NAME+" IN "
                +" ( SELECT DISTINCT "+fj+"."+ DBFormationPlayerDAO.PLAYER_ID_FIELD_NAME
                +" FROM "+f+" , "+fj
                +" WHERE "+f+"."+DBFormationDAO.TEAM_ID_FIELD_NAME +"="+teamId
                +" AND " +f+"."+DBFormationDAO.ID_FIELD_NAME+" = "+fj+"."+ DBFormationPlayerDAO.FORMATION_ID_FIELD_NAME
                +" AND " +f+"."+DBFormationDAO.FORMATION_DEFAULT_FIELD_NAME +"="+Formation.DEFAULT_FORMATION +")";


        Cursor c = super.mDb.rawQuery(rawQuery,
                null);
        return cursorToListPlayer(c);
    }

    public List<Player> getLastSelection(int teamId){
        String fj = DBFormationPlayerDAO.TABLE_NAME;
        String f = DBFormationDAO.TABLE_NAME;
        String rawQuery = " SELECT *"
                +" FROM "+TABLE_NAME
                +" WHERE "+TABLE_NAME+"."+ID_FIELD_NAME+" IN "
                +" ( SELECT DISTINCT "+fj+"."+ DBFormationPlayerDAO.PLAYER_ID_FIELD_NAME
                +" FROM "+f+" , "+fj
                +" WHERE "+f+"."+DBFormationDAO.TEAM_ID_FIELD_NAME +"="+teamId
                +" AND " +f+"."+DBFormationDAO.ID_FIELD_NAME+" = "+fj+"."+ DBFormationPlayerDAO.FORMATION_ID_FIELD_NAME
                +" AND " +f+"."+DBFormationDAO.FORMATION_DEFAULT_FIELD_NAME +"="+Formation.LAST+")";


        Cursor c = super.mDb.rawQuery(rawQuery,
                null);
        return cursorToListPlayer(c);
    }

    public List<Player> getFormationMatch(int formationId){
        String fj = DBFormationPlayerDAO.TABLE_NAME;
        String rawQuery = " SELECT "+TABLE_NAME+".*"
                +" FROM "+TABLE_NAME+" , "+fj
                +" WHERE "+fj+"."+ DBFormationPlayerDAO.FORMATION_ID_FIELD_NAME+"="+formationId
                +" AND "+TABLE_NAME+"."+ID_FIELD_NAME+"="+fj+"."+ DBFormationPlayerDAO.PLAYER_ID_FIELD_NAME;


        Cursor c = super.mDb.rawQuery(rawQuery,
                null);
        return cursorToListPlayer(c);
    }

    private List<Player> cursorToListPlayer(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Player> listPlayers = new ArrayList<Player>();
        listPlayers.clear();

        if (c.moveToFirst()) {
            do {
                Player player = new Player();
                player.setId(c.getInt(NUM_COL_ID));
                player.setSurname(c.getString(NUM_COL_SURNAME));
                player.setFirstName(c.getString(NUM_COL_FIRST_NAME));
                player.setPseudo(c.getString(NUM_COL_PSEUDO));
                player.setNumber(c.getString(NUM_COL_NUMBER));
                listPlayers.add(player);
            } while (c.moveToNext());
        }
        c.close();
        return listPlayers;
    }

    private Player cursorToPlayer(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Player player = new Player();

        player.setId(c.getInt(NUM_COL_ID));
        player.setSurname(c.getString(NUM_COL_SURNAME));
        player.setFirstName(c.getString(NUM_COL_FIRST_NAME));
        player.setPseudo(c.getString(NUM_COL_PSEUDO));
        player.setNumber(c.getString(NUM_COL_NUMBER));

        c.close();

        return player;
    }
}
