package com.example.myhospital.ui.apotek // Sesuaikan dengan package Anda

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Untuk memuat gambar obat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.myhospital.R
import com.example.myhospital.data.Category
import com.example.myhospital.data.Medicine
import java.util.Locale

class ApotekOnlineFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var tilSearchMedicine: TextInputLayout
    private lateinit var etSearchMedicine: TextInputEditText
    private lateinit var rvCategories: RecyclerView
    private lateinit var rvMedicines: RecyclerView
    private lateinit var tvNoMedicineFound: TextView

    private lateinit var medicineAdapter: MedicineAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val filteredMedicines = mutableListOf<Medicine>()
    private var currentCategoryFilter: String? = null

    // Dummy Data Obat-obatan (Ganti dengan data asli Anda)
    private val allMedicines = listOf(
        Medicine("M001", "Paracetamol 500mg", 15000.0, "Obat penurun panas dan pereda nyeri.", "https://d1v9q8vjgpqfs.cloudfront.net/images/product/paracetamol.webp", "Pereda Nyeri", 100),
        Medicine("M002", "Amoxicillin 500mg", 35000.0, "Antibiotik untuk infeksi bakteri.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_xNqXqXQ4Y_c-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v&s", "Antibiotik", 50),
        Medicine("M003", "Vitamin C 1000mg", 25000.0, "Suplemen untuk daya tahan tubuh.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_xNqXqXQ4Y_c-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v&s", "Suplemen", 200),
        Medicine("M004", "Obat Batuk Syrup", 20000.0, "Meredakan batuk berdahak dan kering.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_xNqXqXQ4Y_c-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v&s", "Obat Batuk & Flu", 75),
        Medicine("M005", "Antasida Doen", 10000.0, "Meredakan gejala maag dan asam lambung.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_xNqXqXQ4Y_c-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v&s", "Pencernaan", 120),
        Medicine("M006", "Obat Pilek Tablet", 18000.0, "Meredakan hidung tersumbat dan bersin.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_xNqXqXQ4Y_c-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v&s", "Obat Batuk & Flu", 90),
        Medicine("M007", "Salonpas Gel", 22000.0, "Meredakan nyeri otot dan sendi.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_xNqXqXQ4Y_c-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v&s", "Pereda Nyeri", 60),
        Medicine("M008", "Bodrex Flu & Batuk", 17500.0, "Obat untuk gejala flu dan batuk.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_xNqXqXQ4Y_c-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v-0w9v&s", "Obat Batuk & Flu", 80)
    )

    // Dummy Data Kategori
    private val allCategories = listOf(
        Category("CAT000", "Semua", R.drawable.ic_all_categories), // Ikon semua
        Category("CAT001", "Pereda Nyeri", R.drawable.ic_pain_relief),
        Category("CAT002", "Antibiotik", R.drawable.ic_antibiotics),
        Category("CAT003", "Suplemen", R.drawable.ic_supplements),
        Category("CAT004", "Obat Batuk & Flu", R.drawable.ic_cough_cold),
        Category("CAT005", "Pencernaan", R.drawable.ic_digestive)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apotek_online, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Views
        toolbar = view.findViewById(R.id.toolbar_apotek_online)
        tilSearchMedicine = view.findViewById(R.id.tilSearchMedicine)
        etSearchMedicine = view.findViewById(R.id.etSearchMedicine)
        rvCategories = view.findViewById(R.id.rvCategories)
        rvMedicines = view.findViewById(R.id.rvMedicines)
        tvNoMedicineFound = view.findViewById(R.id.tvNoMedicineFound)

        // --- Setup Toolbar ---
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Apotek Online"
            setDisplayHomeAsUpEnabled(true) // Tombol kembali
        }
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        // --- Setup Kategori RecyclerView (Horizontal) ---
        rvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(allCategories) { category ->
            currentCategoryFilter = if (category.id == "CAT000") null else category.name // 'Semua' berarti tidak ada filter kategori
            filterMedicines(etSearchMedicine.text.toString()) // Terapkan filter
            Toast.makeText(requireContext(), "Kategori: ${category.name}", Toast.LENGTH_SHORT).show()
        }
        rvCategories.adapter = categoryAdapter

        // --- Setup Obat-obatan RecyclerView (Grid) ---
        rvMedicines.layoutManager = GridLayoutManager(requireContext(), 2) // 2 kolom
        medicineAdapter = MedicineAdapter(filteredMedicines) { medicine ->
            // Aksi saat item obat diklik (misal: tampilkan detail obat)
            Toast.makeText(requireContext(), "Melihat detail ${medicine.name}", Toast.LENGTH_SHORT).show()
            // TODO: Navigasi ke halaman detail obat jika diperlukan
        }
        rvMedicines.adapter = medicineAdapter

        // --- Setup Pencarian Obat ---
        etSearchMedicine.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterMedicines(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Muat semua obat saat pertama kali dibuka
        filterMedicines("")
    }

    private fun filterMedicines(query: String) {
        filteredMedicines.clear()
        val lowerCaseQuery = query.toLowerCase(Locale.getDefault())

        val filteredByCategory = if (currentCategoryFilter != null) {
            allMedicines.filter { it.category == currentCategoryFilter }
        } else {
            allMedicines
        }

        for (medicine in filteredByCategory) {
            if (medicine.name.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery) ||
                medicine.description.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery) ||
                medicine.category.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)) {
                filteredMedicines.add(medicine)
            }
        }
        medicineAdapter.notifyDataSetChanged()
        tvNoMedicineFound.visibility = if (filteredMedicines.isEmpty()) View.VISIBLE else View.GONE
    }
}