package com.example.myhospital.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myhospital.R
import com.example.myhospital.data.AntrianRepository
import com.example.myhospital.data.Reservasi
import kotlin.random.Random

class ReservasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservasi)

        val spinnerPoli: Spinner = findViewById(R.id.spinnerPoli)
        val etKeluhan: EditText = findViewById(R.id.etKeluhan)
        val btnSimpan: Button = findViewById(R.id.btnSimpanReservasi)

        // Data untuk dropdown
        val poliOptions = arrayOf("Poli Umum", "Poli Gigi", "Poli Anak", "Poli THT")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, poliOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPoli.adapter = adapter

        btnSimpan.setOnClickListener {
            val poliTerpilih = spinnerPoli.selectedItem.toString()
            val keluhan = etKeluhan.text.toString()

            if (keluhan.isNotEmpty()) {
                // Generate nomor antrian acak
                val nomorAntrian = "FB-${Random.nextInt(1, 100).toString().padStart(3, '0')}"

                // Simpan reservasi ke repository sementara
                AntrianRepository.reservasiAktif = Reservasi(nomorAntrian, poliTerpilih, keluhan)

                Toast.makeText(this, "Reservasi berhasil! Nomor antrian Anda: $nomorAntrian", Toast.LENGTH_LONG).show()
                finish() // Menutup halaman ini dan kembali ke MainActivity
            } else {
                Toast.makeText(this, "Keluhan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}