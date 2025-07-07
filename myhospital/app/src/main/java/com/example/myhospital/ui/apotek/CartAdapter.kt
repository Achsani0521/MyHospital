package com.example.myhospital.ui.apotek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.example.myhospital.R
import com.example.myhospital.data.CartItem
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private val onQuantityChange: (medicineId: String, newQuantity: Int) -> Unit,
    private val onRemoveItem: (medicineId: String) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_medicine, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(cartItem)
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardCartMedicine: MaterialCardView = itemView.findViewById(R.id.cardCartMedicine)  // Perlu ID yang benar
        private val ivCartMedicineImage: ImageView = itemView.findViewById(R.id.ivCartMedicineImage)
        private val tvCartMedicineName: TextView = itemView.findViewById(R.id.tvCartMedicineName)
        private val tvCartMedicinePrice: TextView = itemView.findViewById(R.id.tvCartMedicinePrice)
        private val tvCartQuantity: TextView = itemView.findViewById(R.id.tvCartQuantity)
        private val btnRemoveOne: ImageView = itemView.findViewById(R.id.btnRemoveOne)
        private val btnAddOne: ImageView = itemView.findViewById(R.id.btnAddOne)
        private val btnRemoveItem: ImageView = itemView.findViewById(R.id.btnRemoveItem)

        fun bind(cartItem: CartItem) {
            tvCartMedicineName.text = cartItem.medicine.name

            val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formatter.minimumFractionDigits = 0
            tvCartMedicinePrice.text = formatter.format(cartItem.medicine.price)

            tvCartQuantity.text = cartItem.quantity.toString()

            Glide.with(itemView.context)
                .load(cartItem.medicine.imageUrl)
                .placeholder(R.drawable.ic_medicine_placeholder)
                .error(R.drawable.ic_medicine_placeholder)
                .into(ivCartMedicineImage)

            btnRemoveOne.setOnClickListener {
                onQuantityChange(cartItem.medicine.id, cartItem.quantity - 1)
            }

            btnAddOne.setOnClickListener {
                onQuantityChange(cartItem.medicine.id, cartItem.quantity + 1)
            }

            btnRemoveItem.setOnClickListener {
                onRemoveItem(cartItem.medicine.id)
            }
        }
    }
}

class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.medicine.id == newItem.medicine.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}