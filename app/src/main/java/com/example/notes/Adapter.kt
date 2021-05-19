package com.example.notes

import android.content.Context
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    var notes : List<Note>,
    var listener : OnCardItemClick
) : RecyclerView.Adapter<Adapter.Holder>() {


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        var change = itemView.findViewById<ImageView>(R.id.change)
        var delete = itemView.findViewById<ImageView>(R.id.delete)
        var cardView = itemView.findViewById<CardView>(R.id.card)
        val title = itemView.findViewById<TextView>(R.id.title_maket)
        val body = itemView.findViewById<TextView>(R.id.body_maket)
        val image = itemView.findViewById<ImageView>(R.id.imageView)
        init{
            change.setOnClickListener(this)
            delete.setOnClickListener(this)
            cardView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            when(v?.id) {
                R.id.change -> listener.onChangeClick(adapterPosition)
                R.id.delete -> listener.onDeleteClick(adapterPosition)
                R.id.card -> listener.onCardClick(adapterPosition)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.maket, parent, false)
        val holder = Holder(view)
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val currentNote: Note = notes.get(position)
        holder.title.setText(currentNote.title)
        holder.body.setText(currentNote.body)
        if (!currentNote.readen){
            holder.image.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
        }else{
            holder.image.setImageResource(R.drawable.ic_baseline_check_circle_24)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }


interface OnCardItemClick{
    fun onCardClick(position: Int)
    fun onChangeClick(position: Int)
    fun onDeleteClick(position: Int)
}
}