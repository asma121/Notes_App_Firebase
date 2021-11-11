package com.example.notesappfirebase

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    lateinit var etNote: EditText
    lateinit var buAddNote: Button
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv=findViewById(R.id.rv)
        etNote=findViewById(R.id.etNote)
        buAddNote=findViewById(R.id.buAddNote)

        myViewModel.getNotes().observe(this,{
            notes-> rv.adapter=myAdapter(notes,this)
            rv.layoutManager= LinearLayoutManager(this)
        })


        buAddNote.setOnClickListener {
            myViewModel.insertNote(etNote.text.toString())
            etNote.text.clear()
        }
    }

    fun dailog(note:Note){
        val builder = AlertDialog.Builder(this)
        val input= EditText(this)
        input.setText(note.note)
        builder.setPositiveButton("Save", DialogInterface.OnClickListener{
                dialog, id -> myViewModel.updateNote(note.id,input.text.toString())
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener(){
                dialog, id -> dialog.cancel()
        })

        val alert = builder.create()
        alert.setTitle("Update Note")
        alert.setView(input)
        alert.show()
    }

    fun recyclerView(notes:List<Note>){
        rv.adapter=myAdapter(notes,this)
        rv.layoutManager= LinearLayoutManager(this)
    }
}