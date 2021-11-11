package com.example.notesappfirebase

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyViewModel(application: Application): AndroidViewModel(application) {
    private val notes: MutableLiveData<List<Note>> = MutableLiveData()
    val db = Firebase.firestore
    val TAG="iamMainActivity"


    fun getNotes(): LiveData<List<Note>>{
        getData()
        return notes
    }

    fun insertNote(n:String){
        if (n.isNotEmpty()){
            val note= hashMapOf( "note" to n)
            db.collection("notes")
                .add(note)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    getData()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }else{
            Toast.makeText(getApplication(), "please add Note", Toast.LENGTH_SHORT).show();
        }
    }

    fun updateNote(id: String, s:String){
        if(s.isNotEmpty()) {
           db.collection("notes").document(id).update("note",s)
            getData()
        }
    }

    fun deleteNote(id: String){
        db.collection("notes").document(id).delete()
        getData()
    }

    fun getData(){
        val tempNotes= arrayListOf<Note>()
        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    tempNotes.add(Note(document.id,document.get("note").toString()))
                    //document.data.map { (key,value)-> tempNotes.add(Note(document.id,value.toString())) }
                }
                notes.postValue(tempNotes)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

}