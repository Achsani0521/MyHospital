package com.example.myhospital.ui.apotek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.example.myhospital.R
import com.example.myhospital.data.CartItem
import com.example.myhospital.data.ShoppingCart
import java.text.NumberFormat
import java.util.Locale

class ShoppingCartFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var rvCartItems: RecyclerView
    private lateinit var tvEmptyCart: TextView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnCheckout: MaterialButton
    private lateinit var cardCheckoutSummary: MaterialCardView

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Views
        toolbar = view.findViewById(R.id.toolbar_shopping_cart)
        rvCartItems = view.findViewById(R.id.rvCartItems)
        tvEmptyCart = view.findViewById(R.id.tvEmptyCart)
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice)
        btnCheckout = view.findViewById(R.id.btnCheckout)
        cardCheckoutSummary = view.findViewById(R.id.cardCheckoutSummary)

        // --- Setup Toolbar ---
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Keranjang Belanja"
            setDisplayHomeAsUpEnabled(true) // Tombol kembali
        }
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        // --- Setup RecyclerView Keranjang ---
        rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter = CartAdapter(
            onQuantityChange = { medicineId, newQuantity ->
                ShoppingCart.updateItemQuantity(medicineId, newQuantity)
                updateCartUI()
            },
            onRemoveItem = { medicineId ->
                ShoppingCart.removeItem(medicineId)
                updateCartUI()
            }
        )
        rvCartItems.adapter = cartAdapter

        // --- Setup Tombol Checkout ---
        btnCheckout.setOnClickListener {
            if (ShoppingCart.items.isNotEmpty()) {
                Toast.makeText(requireContext(), "Melanjutkan ke Pembayaran (Total: ${tvTotalPrice.text})", Toast.LENGTH_SHORT).show()
                // TODO: Navigasi ke halaman checkout
                // ShoppingCart.clearCart() // Bersihkan keranjang setelah checkout
                // updateCartUI()
            } else {
                Toast.makeText(requireContext(), "Keranjang Anda kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        // Perbarui UI keranjang saat fragment pertama kali dimuat
        updateCartUI()
    }

    override fun onResume() {
        super.onResume()
        updateCartUI() // Perbarui UI setiap kali fragment kembali aktif
    }

    private fun updateCartUI() {
        val cartItems = ShoppingCart.items
        cartAdapter.submitList(cartItems) // Update adapter dengan data terbaru

        if (cartItems.isEmpty()) {
            rvCartItems.visibility = View.GONE
            cardCheckoutSummary.visibility = View.GONE
            tvEmptyCart.visibility = View.VISIBLE
        } else {
            rvCartItems.visibility = View.VISIBLE
            cardCheckoutSummary.visibility = View.VISIBLE
            tvEmptyCart.visibility = View.GONE

            val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            formatter.minimumFractionDigits = 0
            tvTotalPrice.text = formatter.format(ShoppingCart.getTotalPrice())
        }
    }
}