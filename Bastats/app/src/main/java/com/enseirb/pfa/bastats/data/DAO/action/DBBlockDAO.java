package com.enseirb.pfa.bastats.data.DAO.action;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.enseirb.pfa.bastats.data.DAO.BaseDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayerDAO;
import com.enseirb.pfa.bastats.data.DAO.DBPlayingTimeDAO;
import com.enseirb.pfa.bastats.data.model.Player;
import com.enseirb.pfa.bastats.data.model.action.Block;

import java.util.ArrayList;
import java.util.List;


public class DBBlockDAO extends BaseDAO {
    public static final String TABLE_NAME = "BLOCK";

    private static final String ID_FIELD_NAME                 = "_ID";
    private static final String ACTION_ID_FIELD_NAME          = "ACTION_ID";
    private static final String PLAYER_TARGETED_ID_FIELD_NAME = "PLAYER_TARGETED_ID";


    private static final String ID_FIELD_TYPE                 = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String ACTION_ID_FIELD_TYPE          = "INTEGER";
    private static final String PLAYER_TARGETED_ID_FIELD_TYPE = "INTEGER";


    private static final int NUM_COL_ID                     = 0;
    private static final int NUM_COL_ACTION                 = 2;
    private static final int NUM_COL_PLAYER_TARGETED_ID     = 1;


    public static final String CREATE_TABLE_STATEMENT = ID_FIELD_NAME + " " + ID_FIELD_TYPE
            + ", " + PLAYER_TARGETED_ID_FIELD_NAME + " " + PLAYER_TARGETED_ID_FIELD_TYPE
            + ", " + ACTION_ID_FIELD_NAME+ " " + ACTION_ID_FIELD_TYPE
            + ", " + "FOREIGN KEY (" + PLAYER_TARGETED_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBPlayerDAO.TABLE_NAME+"(ID)"
            + ", " + "FOREIGN KEY (" + ACTION_ID_FIELD_NAME +") "
            + "REFERENCES "+ DBActionDAO.TABLE_NAME+"(ID) ";

    public DBBlockDAO(Context context) {
        super(context);
        this.mDb = this.open();
    }




    public String BlocksJSON(int matchId, List<Player> players){
        StringBuilder str=new StringBuilder();


        for(Player j: players) {
            List<Block> blocks =getAll(j,matchId);
            if(blocks !=null) {
                for (Block block : blocks) {
                    String blockJSON = BlockJSON(block.getId());

                    str.append(blockJSON);

                }
            }
        }

        return str.toString();
    }

    public List<Block> getAll(Player j,int matchId){
        int playerId=j.getId();


        Cursor c = super.mDb.rawQuery("SELECT "+TABLE_NAME+".*"+
                        " FROM "+TABLE_NAME+" , "+DBActionDAO.TABLE_NAME+" , "+ DBPlayingTimeDAO.TABLE_NAME+
                        " WHERE " +DBActionDAO.TABLE_NAME+"."+DBActionDAO.PLAYER_ACTOR_ID_FIELD_NAME +"="+playerId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.MATCH_ID_FIELD_NAME+"="+matchId
                        +" AND "+ DBPlayingTimeDAO.TABLE_NAME+"."+ DBPlayingTimeDAO.ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.TEMPS_DE_JEU_FIELD_NAME
                        +" AND "+ DBBlockDAO.TABLE_NAME+"."+ DBBlockDAO.ACTION_ID_FIELD_NAME+"="+DBActionDAO.TABLE_NAME+"."+DBActionDAO.ID_FIELD_NAME
                ,
                null);
        return cursorToListBlock(c);

    }





    public String BlockJSON(int id){
        StringBuilder str=new StringBuilder("{\"TypeAction\" :{");
        Cursor c = super.mDb.query(TABLE_NAME, null,ID_FIELD_NAME +"=" + id, null, null, null, null);
        c.moveToFirst();
        int actionId=c.getInt(NUM_COL_ACTION);
        DBActionDAO dba=new DBActionDAO(mCtx);

        String actionJSON=dba.ActionJSON(actionId);
        str.append("\""+"Type:"+"\": "+"\""+"BLOCK"+"\""+" ,\n");

        for(int i=2;i<c.getColumnCount();i++) {
            String columnName=c.getColumnName(i), columnValue=c.getString(i);
            str.append("\""+columnName+"\": "+"\""+columnValue+"\""+" ,\n");

        }
        str.append("},\n");
        str.append(actionJSON);
        return str.toString();
    }






    public long insert(Block block){
        ContentValues values = new ContentValues();
        DBActionDAO dba=new DBActionDAO(mCtx);
        dba.insert(block);

        values.put(ACTION_ID_FIELD_NAME,dba.getLast().getActionId());
        values.put(PLAYER_TARGETED_ID_FIELD_NAME, block.getTargetedPlayer());
        return  mDb.insert(TABLE_NAME, null, values);
    }

    public long update(int id, Block block){
        ContentValues values = new ContentValues();
        values.put(PLAYER_TARGETED_ID_FIELD_NAME, block.getTargetedPlayer());


        return mDb.update(TABLE_NAME, values, ID_FIELD_NAME + " = " + id, null);
    }


    public int removeWithId(int id){
        int actionId=getWithId(id).getActionId();
        mDb.delete("ACTION","ID="+actionId,null);
        return mDb.delete(TABLE_NAME, ID_FIELD_NAME + " = " +id, null);
    }


    public Block getWithId(int id){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                ID_FIELD_NAME +"=" + id, null, null, null, null);
        return cursorToBlock(c);
    }


    public List<Block> getAll(){
        Cursor c = super.mDb.query(TABLE_NAME, null,
                null, null, null, null, null);
        return cursorToListBlock(c);
    }


    private List<Block> cursorToListBlock(Cursor c){
        if (c.getCount() == 0)
            return null;

        List<Block> list = new ArrayList<Block>();
        list.clear();

        if (c.moveToFirst()) {
            do {
                Block block = new Block();
                block.setId(c.getInt(NUM_COL_ID));
                block.setActionId(c.getInt(NUM_COL_ACTION));
                block.setTargetedPlayer(c.getInt(NUM_COL_PLAYER_TARGETED_ID));
                list.add(block);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }


    private Block cursorToBlock(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Block block = new Block();

        block.setId(c.getInt(NUM_COL_ID));
        block.setActionId(c.getInt(NUM_COL_ACTION));
        block.setTargetedPlayer(c.getInt(NUM_COL_PLAYER_TARGETED_ID));

        c.close();

        return block;
    }

    public Block getLast(){
    Cursor c = mDb.query(TABLE_NAME,null, null, null, null, null, ID_FIELD_NAME+" DESC", null);
    c.moveToLast();
    return cursorToBlock(c);
}

}
