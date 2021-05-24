package com.example.notes

import com.example.notes.Fragments.AddingFragment
import com.example.notes.Fragments.FragmentMain
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var nav: NavigationView
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment(FragmentMain())

        val drawer = findViewById<DrawerLayout>(R.id.drawer)

        nav = findViewById(R.id.navigation)

        toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.deleted -> Toast.makeText(this, "Deleted Notes", Toast.LENGTH_SHORT).show()
                R.id.profile -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                R.id.exit -> System.exit(0)
            }
            true
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            setFragment(AddingFragment())
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}