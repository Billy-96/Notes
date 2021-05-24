package com.example.notes.Fragments

import com.example.notes.Adapters.Adapter
import com.example.notes.Utils.Utils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.*
import com.google.firebase.firestore.FirebaseFirestore

class FragmentMain : Fragment(), Adapter.OnCardItemClick {
    lateinit var adapter: Adapter
    lateinit var recyclerView: RecyclerView
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycle)
        retrieveData()

    }

    private fun configureList(list: MutableList<Note>) {
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        adapter = Adapter(Utils.notes, this)
        recyclerView.adapter = adapter
    }

    private fun retrieveData() {
        Utils.notes.clear()
        db.collection("notes")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        val note = Note(
                            document.data["title"] as String,
                            document.data["body"] as String,
                            document.data["time"] as String,
                            document.data["id"] as String
                        )
                        Utils.notes.add(note)
                    }
                    configureList(Utils.notes)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failure: $e", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onCardClick(position: Int) {
        var bundle = Bundle()
        bundle.putSerializable(Utils.key, Utils.notes[position])

        val fragment = FragmentDescription()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onChangeClick(position: Int) {
        var bundle = Bundle()
        bundle.putSerializable(Utils.key, Utils.notes[position])

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
                    db.collection("notes")
                        .document(Utils.notes[position].id)
                        .delete()
                        .addOnSuccessListener { _ ->
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failure: $e", Toast.LENGTH_SHORT).show()
                        }
                    Utils.notes.removeAt(position)
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
