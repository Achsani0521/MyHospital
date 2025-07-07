package com.example.myhospital.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myhospital.R
import com.example.myhospital.ui.ReservasiActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val btnReservasi: Button = view.findViewById(R.id.btnReservasi)

        // Mengambil username dari bundle yang dikirim MainActivity
        val username = arguments?.getString("USERNAME")
        tvUsername.text = username

        btnReservasi.setOnClickListener {
            val intent = Intent(activity, ReservasiActivity::class.java)
            startActivity(intent)
        }
    }
}