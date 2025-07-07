package com.example.myhospital.data

object ShoppingCart {
    private val _items = mutableListOf<CartItem>()
    val items: List<CartItem> get() = _items // Read-only list

    fun addItem(medicine: Medicine, quantity: Int = 1) {
        val existingItem = _items.find { it.medicine.id == medicine.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            _items.add(CartItem(medicine, quantity))
        }
    }

    fun updateItemQuantity(medicineId: String, newQuantity: Int) {
        val item = _items.find { it.medicine.id == medicineId }
        if (item != null) {
            if (newQuantity > 0) {
                item.quantity = newQuantity
            } else {
                _items.remove(item) // Hapus jika kuantitas 0 atau kurang
            }
        }
    }

    fun removeItem(medicineId: String) {
        _items.removeAll { it.medicine.id == medicineId }
    }

    fun getTotalPrice(): Double {
        return _items.sumOf { it.medicine.price * it.quantity }
    }

    fun clearCart() {
        _items.clear()
    }
}