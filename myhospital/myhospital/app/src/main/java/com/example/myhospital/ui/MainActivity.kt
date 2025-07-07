package com.example.myhospital.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myhospital.R
import com.example.myhospital.ui.antrian.AntrianFragment
import com.example.myhospital.ui.home.HomeFragment
import com.example.myhospital.ui.profil.ProfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)

        val username = intent.getStringExtra("USERNAME") ?: "Pengguna"

        // Bundle untuk mengirim data username ke setiap fragment
        val bundle = Bundle().apply {
            putString("USERNAME", username)
        }

        // Set fragment default saat pertama kali dibuka
        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            homeFragment.arguments = bundle
            loadFragment(homeFragment)
        }

        bottomNav.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.navigation_antrian -> AntrianFragment()
                R.id.navigation_profil -> ProfilFragment().apply { arguments = bundle }
                else -> HomeFragment().apply { arguments = bundle }
            }
            loadFragment(selectedFragment)
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}