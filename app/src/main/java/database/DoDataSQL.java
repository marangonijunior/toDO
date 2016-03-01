package database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import entidade.ToDO;

/**
 * Created by hedneijr on 29/01/2016.
 */
public class DoDataSQL extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "dbHednei";

    public static final String TB_TODO = "todo";
    public static final String TODO_ID = "_id";
    public static final String TODO_TITULO = "titulo";
    public static final String TODO_DETALHES = "detalhes";
    public static final String TODO_URGENCIA = "urgencia";
    public static final int DATABASE_VERSION = 1;

    public DoDataSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DoDataSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DoDataSQL(Context c){
        this(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TODO = "create table " +
                TB_TODO + "( " + TODO_ID       + " integer primary key autoincrement, " +
                TODO_TITULO     + " text, " +
                TODO_DETALHES     + " text, " +
                TODO_URGENCIA + " text)";
        database.execSQL(CREATE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_TODO);
        onCreate(db);
    }
}
