package com.example.notes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    lateinit var nav : NavigationView
    val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawer = findViewById<DrawerLayout>(R.id.drawer)

        nav = findViewById(R.id.navigation)

        val sharedPreferences =
            getSharedPreferences("list", Context.MODE_PRIVATE)
        if (sharedPreferences?.getString("list", null) != null) {
            val listType = object : TypeToken<MutableList<Note>>() {}.type
            val json = sharedPreferences.getString("list", null)
            val userNotes: MutableList<Note> = gson.fromJson(json, listType)
            Utils.notes = userNotes
            Log.i("MyTag", "It works")
        }

        toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.deleted -> Toast.makeText(this,"Deleted Notes", Toast.LENGTH_SHORT).show()
                R.id.profile -> Toast.makeText(this,"Profile", Toast.LENGTH_SHORT).show()
                R.id.exit -> System.exit(0)
            }
            true
        }

        val fragmentMain = FragmentMain()
        val fragmentDescription = FragmentDescription()
        val adding = AddingFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragmentMain)
            commit()
        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener({
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, adding)
                commit()
            }
        })

        setSharedPreferences(Utils.notes)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    public fun setSharedPreferences(userNotes: MutableList<Note>) {
        val sharedPreference = getSharedPreferences("list", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        val userNotesString = gson.toJson(userNotes)
        editor?.putString("list", userNotesString)
        editor?.apply()
    }

}