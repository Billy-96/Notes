package com.example.notes.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.notes.Note
import com.example.notes.R
import com.example.notes.Utils.Utils

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
        view.findViewById<TextView>(R.id.time_description).text =  note.time
    }
}