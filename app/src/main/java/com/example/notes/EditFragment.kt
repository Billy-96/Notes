package com.example.notes

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import org.w3c.dom.Text
import java.text.DateFormat


class EditFragment : Fragment() {
    lateinit var editTitle : EditText
    lateinit var editTime : TextView
    lateinit var editDescription : EditText
    lateinit var save : Button
    val gson = Gson()

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
        editTime.text = DateFormat.getDateInstance().format(note.time).toString()
        editDescription.setText(note.body)

        save.setOnClickListener({
            note.title = editTitle.text.toString()
            note.body = editDescription.text.toString()
            setSharedPreferences(Utils.notes)
        })


    }
    private fun setSharedPreferences(userNotes: MutableList<Note>) {
        val sharedPreference =
            context?.getSharedPreferences("list", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        val userNotesString = gson.toJson(userNotes)
        editor?.putString("list", userNotesString)
        editor?.apply()
    }
}
