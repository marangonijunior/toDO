package controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import database.DoDataSQL;
import entidade.ToDO;

/**
 * Created by hedneijr on 12/02/2016.
 */
public class ToDOController {


    public Context mContext = null;
    public DoDataSQL dataSQL    ;
    public SQLiteDatabase db;
    List<ToDO> todoList = new ArrayList<ToDO>();


    public ToDOController(Context context) {
        this.mContext = context;
        dataSQL = new DoDataSQL(context);
    }

    public void insertToDo(ToDO todo){

        db = dataSQL.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DoDataSQL.TODO_TITULO, todo.getTitulo());
        values.put(DoDataSQL.TODO_DETALHES, todo.getDetalhe());
        values.put(DoDataSQL.TODO_URGENCIA, todo.getUrgencia());

        db.insert(DoDataSQL.TB_TODO, null, values);
        db.close();
    }


    public List<ToDO> listToDO() {
        Cursor cursor;
        dataSQL = new DoDataSQL(mContext);
        String[] campos = {DoDataSQL.TODO_TITULO,DoDataSQL.TODO_DETALHES, DoDataSQL.TODO_URGENCIA, DoDataSQL.TODO_ID};
        db = dataSQL.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DoDataSQL.TB_TODO,null);

        // Percorendo o resultado da query
        if (cursor.moveToFirst()) {
            do {
                ToDO todos = new ToDO();
                todos.setTitulo(cursor.getString(1));
                todos.setDetalhe(cursor.getString(2));
                todos.setUrgencia(cursor.getString(3));
                // Adicionado
                todoList.add(todos);
            } while (cursor.moveToNext());

        }
        db.close();
        return todoList;
    }


}
