package com.example.myhospital.data

data class CartItem(
    val medicine: Medicine, // Referensi ke objek Medicine yang dibeli
    var quantity: Int       // Jumlah obat yang dibeli
)