package com.example.notes

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import java.util.*

class AddingFragment : Fragment() {
    lateinit var bodyAdding : EditText
    lateinit var titleAdding : EditText
    lateinit var buttonAdding : Button
    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bodyAdding = view.findViewById(R.id.edit_text_adding)
        buttonAdding = view.findViewById(R.id.button_next)
        buttonAdding.setOnClickListener({
            if (bodyAdding.text.length !=0) {
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Добавление")
                    .setMessage("Введите пожалуйста название вашей записки")
                    .setIcon(R.drawable.ic_baseline_add_box_24)
                val maket = LayoutInflater.from(context).inflate(R.layout.dialog_adding, null)
                dialog.setView(maket)
                titleAdding = maket.findViewById(R.id.title_adding)
                dialog.setPositiveButton("Добавить ") { _, _ ->
                    if (titleAdding.text.length != 0) {
                        val myCalendar = Calendar.getInstance()
                        val year = myCalendar.get(Calendar.YEAR)
                        val month = myCalendar.get(Calendar.MONTH)
                        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

                        val note = Note(
                            titleAdding.text.toString(),
                            bodyAdding.text.toString(),
                            Date(year, month, day),
                            false
                        )
                        Utils.notes.add(note)

                        val sharedPreference = context?.getSharedPreferences("list", Context.MODE_PRIVATE)
                        val editor = sharedPreference?.edit()
                        val userNotesString = gson.toJson(Utils.notes)
                        editor?.putString("list", userNotesString)
                        editor?.apply()

                        val mainFragment = FragmentMain()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, mainFragment)
                            .addToBackStack(null)
                            .commit()
                    }else{
                        Toast.makeText(context,"Назовите то, что вы создали",Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.setNegativeButton("Отмена") { _, _ ->

                }
                dialog.show()
            }else{
                Toast.makeText(context,"Заполните",Toast.LENGTH_SHORT).show()
            }
        })



    }


}