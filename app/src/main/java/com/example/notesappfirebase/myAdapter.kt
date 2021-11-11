package com.example.notesappfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class myAdapter(private val notes:List<Note>,val activity: MainActivity): RecyclerView.Adapter<myAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    private val myViewModel by lazy { ViewModelProvider(activity).get(MyViewModel::class.java) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note=notes[position]
        holder.itemView.apply {
            textView.text=note.note

            imageButtonEdit.setOnClickListener {
                activity.dailog(note)
            }

            imageButtonDelete.setOnClickListener {
                myViewModel.deleteNote(note.id)
            }
        }

    }

    override fun getItemCount()=notes.size
}