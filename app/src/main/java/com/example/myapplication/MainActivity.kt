package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.adapter.RecyAdapter
import com.example.myapplication.adapter.recymodel
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemDialogBinding
import com.example.myapplication.mydb.MyDbHelper

class MainActivity : AppCompatActivity(),RecyAdapter.RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var recyAdapter: RecyAdapter
    lateinit var list: ArrayList<recymodel>
    lateinit var myDbHelper: MyDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        myDbHelper = MyDbHelper(this)
        list = ArrayList()
        list.addAll(myDbHelper.getAllContact())
        recyAdapter = RecyAdapter(list,this)
        binding.recy.adapter = recyAdapter
        binding.btnClick.setOnClickListener{
            val alertDialog = AlertDialog.Builder(this)
                .create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            alertDialog.setView(itemDialogBinding.root)
            itemDialogBinding.saveBtn.setOnClickListener {
                val recymodel = recymodel(
                    name = itemDialogBinding.edtName.text.toString(),
                    number = itemDialogBinding.edtNumber.text.toString(),
                )
                list.add(recymodel)
                myDbHelper.addContact(recymodel)
                recyAdapter.notifyDataSetChanged()
                alertDialog.cancel()
            }
            alertDialog.show()
        }

    }

    override fun rvdeleteClick(recymodel: recymodel) {
        myDbHelper.deleteContact(recymodel)
        list.remove(recymodel)
        recyAdapter.notifyDataSetChanged()
    }

    override fun rvEditClick(recymodel: recymodel) {
        val dialog = AlertDialog.Builder(this).create()
        val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
        dialog.setView(itemDialogBinding.root)
        itemDialogBinding.edtName.setText(recymodel.name)
        itemDialogBinding.edtNumber.setText(recymodel.number)

        itemDialogBinding.saveBtn.setOnClickListener {
            val recymodel2 = recymodel(
                id =recymodel.id,
                name = itemDialogBinding.edtName.text.toString(),
                number = itemDialogBinding.edtNumber.text.toString()
            )
            myDbHelper.editContact(recymodel2)
            list.clear()
            list.addAll(myDbHelper.getAllContact())
            recyAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()
     }
}