package com.example.myhospital.ui.apotek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Untuk memuat gambar dari URL
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.example.myhospital.R
import com.example.myhospital.data.Medicine
import com.example.myhospital.data.ShoppingCart
import java.text.NumberFormat
import java.util.Locale

class MedicineAdapter(
    private val medicines: MutableList<Medicine>,
    private val onItemClick: (Medicine) -> Unit
) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicines[position]
        holder.bind(medicine)
        holder.itemView.setOnClickListener { onItemClick(medicine) }
    }

    override fun getItemCount(): Int = medicines.size

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardMedicine: MaterialCardView = itemView.findViewById(R.id.cardMedicine)
        private val ivMedicineImage: ImageView = itemView.findViewById(R.id.ivMedicineImage)
        private val tvMedicineName: TextView = itemView.findViewById(R.id.tvMedicineName)
        private val tvMedicinePrice: TextView = itemView.findViewById(R.id.tvMedicinePrice)
        private val tvMedicineDescription: TextView = itemView.findViewById(R.id.tvMedicineDescription)
        private val btnBuy: MaterialButton = itemView.findViewById(R.id.btnBuy)

        fun bind(medicine: Medicine) {
            tvMedicineName.text = medicine.name
            tvMedicineDescription.text = medicine.description

            val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formatter.minimumFractionDigits = 0
            tvMedicinePrice.text = formatter.format(medicine.price)

            Glide.with(itemView.context)
                .load(medicine.imageUrl)
                .placeholder(R.drawable.ic_medicine_placeholder)
                .error(R.drawable.ic_medicine_placeholder)
                .into(ivMedicineImage)

            btnBuy.setOnClickListener {
                // Tambahkan obat ini ke keranjang belanja
                ShoppingCart.addItem(medicine)
                Toast.makeText(itemView.context, "${medicine.name} ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
                // TODO: Mungkin tambahkan badge jumlah item di keranjang pada Toolbar/Bottom Nav
            }
        }
    }


}