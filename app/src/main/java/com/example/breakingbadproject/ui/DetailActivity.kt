package com.example.breakingbadproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.breakingbadproject.databinding.ActivityDetailBinding
import com.example.breakingbadproject.util.Constants.Companion.CHARACTER_UUID
import com.example.breakingbadproject.util.displayImage
import com.example.breakingbadproject.util.makePlaceholder
import com.example.breakingbadproject.viewmodel.DetailViewModel

private lateinit var binding: ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var databinding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val i = intent.extras
        val id = i?.getInt(CHARACTER_UUID) ?: 0

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        binding.viewmodel = detailViewModel

        detailViewModel.getDetails(id)

        observeLiveData()

    }

    private fun observeLiveData() {
        detailViewModel.charDetailMutableLiveData.observe(this) {
            binding.characterDetails = it
//            binding.name.text = it.name
//            binding.image.displayImage(it.img, makePlaceholder(this))
//            binding.birthday.text = it.birthday
//            binding.nickname.text = it.nickname
//            binding.status.text = it.status
        }
    }
}