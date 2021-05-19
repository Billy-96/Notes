package com.example.notes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*

class FragmentMain : Fragment(), Adapter.OnCardItemClick {
    lateinit var adapter: Adapter
    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Utils.notes.size == 0){
            val note1 = Note("First Title", "First Description",Date(),false)
            val note2 = Note("Second Title", "Second Description", Date() , false)
            val note3 = Note("Third Title", "Third Description", Date(), false)
            Utils.notes.add(note1)
            Utils.notes.add(note2)
            Utils.notes.add(note3)
            Utils.notes.add(Note("Fourth Title","Fourth Description",Date(),false))
        }


        val recyclerView = view.findViewById<RecyclerView>(R.id.recycle)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        adapter = Adapter( Utils.notes,this)
        recyclerView.adapter = adapter

    }

    override fun onCardClick(position: Int) {
        var bundle = Bundle()
        bundle.putSerializable(Utils.key,  Utils.notes[position])

        val fragment = FragmentDescription()
        fragment.arguments = bundle

        Utils.notes[position].readen = true

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onChangeClick(position: Int) {
        var bundle = Bundle()
        bundle.putSerializable(Utils.key,  Utils.notes[position])

        val fragment = EditFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDeleteClick(position: Int) {
        val alertDialog = context?.let {
            AlertDialog.Builder(it)
                .setTitle("Удаление")
                .setMessage("Вы правда хотите удалить заметку про ${Utils.notes[position].title}")
                .setIcon(R.drawable.ic_baseline_delete_24)
                .setPositiveButton("Удалить") { dialog, which ->
                    val sharedPreference =
                        context?.getSharedPreferences("list", Context.MODE_PRIVATE)
                    val editor = sharedPreference?.edit()
                    Utils.notes.removeAt(position)
                    val userNotesString = gson.toJson(Utils.notes)
                    editor?.putString("list", userNotesString)
                    editor?.apply()
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Отмена") { dialog, which ->
                    Toast.makeText(context, "Думайте", Toast.LENGTH_SHORT).show()
                }
                .create()
        }

        alertDialog!!.show()

    }

}
