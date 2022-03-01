package com.example.breakingbadproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.breakingbadproject.R
import com.example.breakingbadproject.databinding.ActivityDetailBinding
import com.example.breakingbadproject.viewmodel.DetailViewModel

private lateinit var binding: ActivityDetailBinding
class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val i = intent.extras
        var id = 0
         if ( i != null) {
            id = i.getInt("charId")
         }

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        detailViewModel.getCharacter(id)

        observe()

    }

    fun observe() {
        detailViewModel.charDetailMutableLiveData.observe(this, Observer {
            binding.name.text = it.name
        })
    }
}