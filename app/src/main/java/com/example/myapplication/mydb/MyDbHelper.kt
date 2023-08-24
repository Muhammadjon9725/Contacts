package com.example.myapplication.mydb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.adapter.recymodel
import java.lang.invoke.ConstantCallSite

class MyDbHelper(context:Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION),MyDbInterface {
    companion object{
        val DB_NAME = "contact_db"
        val DB_VERSION = 1
        val TABLE_NAME = "contact_table"
        val CONTACT_ID = "id"
        val CONTACT_NAME = "name"
        val CONTACT_NUMBER = "number"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table $TABLE_NAME($CONTACT_ID integer not null primary key autoincrement unique, $CONTACT_NAME text not null, $CONTACT_NUMBER text not null)"
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun addContact(recymodel: recymodel) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTACT_NAME,recymodel.name)
        contentValues.put(CONTACT_NUMBER,recymodel.number)
        database.insert(TABLE_NAME,null,contentValues)
        database.close()
    }

    override fun getAllContact(): ArrayList<recymodel> {
        val database = readableDatabase
        val list = ArrayList<recymodel>()
        val query = "select*from $TABLE_NAME"
        val cursor = database.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do {
                val recymodel = recymodel(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    number = cursor.getString(2)
                )
                list.add(recymodel)

            }while (cursor.moveToNext())
        }

        return list
    }

    override fun editContact(recymodel: recymodel) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTACT_ID, recymodel.id)
        contentValues.put(CONTACT_NAME,recymodel.name)
        contentValues.put(CONTACT_NUMBER,recymodel.number)

        database.update(TABLE_NAME,contentValues,"$CONTACT_ID=?", arrayOf(recymodel.id.toString()))
    }

    override fun deleteContact(recymodel: recymodel) {
        val database = writableDatabase
        database.delete(TABLE_NAME,"$CONTACT_ID=?", arrayOf(recymodel.id.toString()))
    }
}