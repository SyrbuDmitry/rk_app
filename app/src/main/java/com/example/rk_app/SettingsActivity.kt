package com.example.rk_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        Preference.OnPreferenceChangeListener  {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            findPreference<EditTextPreference>("days_count")!!.onPreferenceChangeListener = this

        }

        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            if (preference!!.key == "days_count") {
                try {
                    val days = Integer.parseInt(newValue.toString())
                    if (days <= 0) {
                        Toast.makeText(context, "incorrect number", Toast.LENGTH_SHORT).show()
                        return false
                    }
                    return true
                } catch (e: NumberFormatException) {
                    Toast.makeText(context, "incorrect number", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            return true;
        }
    }
}