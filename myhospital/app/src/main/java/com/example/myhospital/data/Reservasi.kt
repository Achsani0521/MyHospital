package com.example.myhospital.data

data class Reservasi(
    val nomorAntrian: String,
    val poli: String,
    val keluhan: String,
    val estimasiWaktu: String = "20 Menit"
)