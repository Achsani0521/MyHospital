package com.example.myhospital.ui.apotek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.example.myhospital.R
import com.example.myhospital.data.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0 // Default to 'All' category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_pill, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, position == selectedPosition)
        holder.itemView.setOnClickListener {
            val oldSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(oldSelectedPosition)
            notifyItemChanged(selectedPosition)
            onItemClick(category)
        }
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardCategory: MaterialCardView = itemView.findViewById(R.id.cardCategory)
        private val ivCategoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
        private val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)

        fun bind(category: Category, isSelected: Boolean) {
            tvCategoryName.text = category.name
            ivCategoryIcon.setImageResource(category.iconResId)

            if (isSelected) {
                cardCategory.setCardBackgroundColor(itemView.context.getColor(R.color.md_blue_500))
                tvCategoryName.setTextColor(itemView.context.getColor(android.R.color.white))
                ivCategoryIcon.setColorFilter(itemView.context.getColor(android.R.color.white))
            } else {
                cardCategory.setCardBackgroundColor(itemView.context.getColor(R.color.white))
                tvCategoryName.setTextColor(itemView.context.getColor(R.color.black))
                ivCategoryIcon.setColorFilter(itemView.context.getColor(R.color.md_blue_500))
            }
        }
    }
}