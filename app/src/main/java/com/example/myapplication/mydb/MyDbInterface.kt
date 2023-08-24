package com.example.myapplication.mydb

import com.example.myapplication.adapter.recymodel

interface MyDbInterface {
    fun addContact( recymodel: recymodel)
    fun getAllContact(): ArrayList<recymodel>
    fun editContact(recymodel: recymodel)
    fun deleteContact(recymodel: recymodel)
}