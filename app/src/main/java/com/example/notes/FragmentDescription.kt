package com.example.notes

import android.icu.text.MessageFormat.format
import android.os.Bundle
import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.time.format.DateTimeFormatter
import java.util.*

class FragmentDescription : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        val note = bundle?.getSerializable(Utils.key) as Note


        view.findViewById<TextView>(R.id.title_description).text = note.title
        view.findViewById<TextView>(R.id.body_description).text = note.body
        view.findViewById<TextView>(R.id.time_description).text =  DateFormat.getDateInstance().format(note.time).toString()

       

    }
}