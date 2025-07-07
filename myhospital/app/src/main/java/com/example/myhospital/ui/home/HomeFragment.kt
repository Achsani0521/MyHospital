package com.example.myhospital.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myhospital.R
import com.example.myhospital.ui.ReservasiActivity
import com.example.myhospital.ui.apotek.ApotekOnlineFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val btnReservasi: Button = view.findViewById(R.id.btnReservasi)
        val cardApotekOnline: com.google.android.material.card.MaterialCardView = view.findViewById(R.id.cardApotekOnline)


        // Mengambil username dari bundle yang dikirim MainActivity
        val username = arguments?.getString("USERNAME")
        tvUsername.text = "Halo, $username"

        btnReservasi.setOnClickListener {
            val intent = Intent(activity, ReservasiActivity::class.java)
            startActivity(intent)
        }

        cardApotekOnline.setOnClickListener {
            // Navigasi ke ApotekOnlineFragment
            // Menggunakan Fragment Transaction (cara manual jika tidak pakai Navigation Component)
            val apotekOnlineFragment = ApotekOnlineFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, apotekOnlineFragment) // Ganti R.id.nav_host_fragment_activity_main dengan ID container Fragment utama Anda di MainActivity
                .addToBackStack(null) // Penting: agar bisa kembali ke HomeFragment dengan tombol back
                .commit()

            // Jika Anda menggunakan Android Navigation Component (SANGAT DIREKOMENDASIKAN):
            // findNavController().navigate(R.id.action_homeFragment_to_apotekOnlineFragment)
            // Anda perlu mendefinisikan action ini di navigation graph Anda (navigation.xml)
        }
    }
}