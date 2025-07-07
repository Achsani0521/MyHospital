package com.example.myhospital.data

data class Medicine(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String, // URL gambar atau referensi drawable
    val category: String,
    val stock: Int
)

