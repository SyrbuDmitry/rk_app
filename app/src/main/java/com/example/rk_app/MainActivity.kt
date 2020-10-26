package com.example.rk_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager

const val APP_PREFERENCES = "root_preferences"

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu);
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onResume() {
        super.onResume()

        val str = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("crypto_type", "BTC")
        findViewById<EditText>(R.id.currencySelected).setText(str, TextView.BufferType.EDITABLE);
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        val id: Int = item.itemId
        if (id==R.id.refresh){
            val crypto = findViewById<EditText>(R.id.currencySelected).text.toString()
            val editor = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
            editor.putString("crypto_type", crypto)
            editor.apply()
        } else if (id == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    fun onLinkClick(view: View) {
        openWebPage(getString(R.string.txt_link_link))
    }

    fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    }
}