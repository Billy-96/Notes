package com.example.notes.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.notes.Note
import com.example.notes.R
import com.example.notes.Utils.Utils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


class EditFragment : Fragment() {
    lateinit var editTitle : EditText
    lateinit var editTime : TextView
    lateinit var editDescription : EditText
    lateinit var save : Button
    val gson = Gson()
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        val note = bundle?.getSerializable(Utils.key) as Note

        save = view.findViewById(R.id.save_button)
        editTitle = view.findViewById<EditText>(R.id.edit_text_title)
        editTime = view.findViewById(R.id.titme_edit)
        editDescription = view.findViewById(R.id.edit_text_description)

        editTitle.setText(note.title)
        editTime.text = note.time.toString()
        editDescription.setText(note.body)

        save.setOnClickListener{
            note.title = editTitle.text.toString()
            note.body = editDescription.text.toString()

            db.collection("notes")
                .document(note.id)
                .update(convertObjectToMap(note))
                .addOnSuccessListener { _->
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{ e ->
                    Toast.makeText(context,"Failure: $e",Toast.LENGTH_SHORT).show()
                }
        }
    }
    private fun convertObjectToMap(note: Note): Map<String,String> {
        return mapOf(
            "title" to note.title,
            "body" to note.body,
            "time" to note.time,
            "id" to note.id
        )
    }
}
