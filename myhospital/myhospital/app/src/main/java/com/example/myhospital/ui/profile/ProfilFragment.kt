package com.example.myhospital.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myhospital.R
import com.example.myhospital.data.AntrianRepository
import com.example.myhospital.ui.LoginActivity

class ProfilFragment : Fragment(R.layout.fragment_profil) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvProfilUsername: TextView = view.findViewById(R.id.tvProfilUsername)
        val btnLogout: Button = view.findViewById(R.id.btnLogout)

        tvProfilUsername.text = arguments?.getString("USERNAME")

        btnLogout.setOnClickListener {
            // Hapus juga data antrian saat logout
            AntrianRepository.reservasiAktif = null

            val intent = Intent(activity, LoginActivity::class.java).apply {
                // Hapus semua activity sebelumnya dari stack
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }
}