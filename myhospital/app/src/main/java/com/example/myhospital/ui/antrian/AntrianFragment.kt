package com.example.myhospital.ui.antrian

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myhospital.R
import com.example.myhospital.data.AntrianRepository
import com.example.myhospital.ui.ReservasiActivity

class AntrianFragment : Fragment(R.layout.fragment_antrian) {

    private lateinit var layoutAdaAntrian: LinearLayout
    private lateinit var layoutTidakAdaAntrian: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutAdaAntrian = view.findViewById(R.id.layoutAdaAntrian)
        layoutTidakAdaAntrian = view.findViewById(R.id.layoutTidakAdaAntrian)

        val btnBatalkan: Button = view.findViewById(R.id.btnBatalkan)
        val btnReservasi: Button = view.findViewById(R.id.btnReservasi)
        btnBatalkan.setOnClickListener {
            // Hapus reservasi dari repository
            AntrianRepository.reservasiAktif = null
            // Perbarui tampilan
            updateTampilan()
        }
        btnReservasi.setOnClickListener {
            val intent = Intent(activity, ReservasiActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Tampilan akan diperbarui setiap kali fragment ini dibuka/kembali aktif
        updateTampilan()
    }

    private fun updateTampilan() {
        val reservasi = AntrianRepository.reservasiAktif
        if (reservasi != null) {
            // Jika ada reservasi, tampilkan tiket
            layoutAdaAntrian.visibility = View.VISIBLE
            layoutTidakAdaAntrian.visibility = View.GONE

            view?.let {
                it.findViewById<TextView>(R.id.tvNomorAntrian)?.text = reservasi.nomorAntrian
                it.findViewById<TextView>(R.id.tvDetailPoli)?.text = "Poli: ${reservasi.poli}"
                it.findViewById<TextView>(R.id.tvDetailKeluhan)?.text = "Keluhan: ${reservasi.keluhan}"
                it.findViewById<TextView>(R.id.tvDetailJam)?.text = "${reservasi.estimasiWaktu}"
                it.findViewById<TextView>(R.id.tvDetailRumahSakit)?.text = "RSUD Dr. Soetomo"
                it.findViewById<TextView>(R.id.tvDetailDokter)?.text = "Dokter: Dr. Budi Santoso"
                it.findViewById<TextView>(R.id.tvDetailTanggal)?.text = "Tanggal: 20 Juni 2025"

            }
        } else {
            // Jika tidak ada, tampilkan pesan kosong
            layoutAdaAntrian.visibility = View.GONE
            layoutTidakAdaAntrian.visibility = View.VISIBLE
        }
    }
}