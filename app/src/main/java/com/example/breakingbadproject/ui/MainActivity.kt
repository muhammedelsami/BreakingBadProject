package com.example.breakingbadproject.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.breakingbadproject.adapter.CharactersAdapter
import com.example.breakingbadproject.databinding.ActivityMainBinding
import com.example.breakingbadproject.model.CharactersModelItem
import com.example.breakingbadproject.viewmodel.CharacterViewModel

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //latent var apiService: ApiService
    //latent var charList: MutableList<CharactersModelItem>

    private lateinit var progressBar : ProgressBar
    private lateinit var errorMsg : TextView
    private lateinit var refresh : SwipeRefreshLayout

    private lateinit var characterViewModel : CharacterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        characterViewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        characterViewModel.refresh()

        progressBar = binding.progressCircular
        errorMsg = binding.errorMessage
        refresh = binding.swipeRefresh

        val recyclerView : RecyclerView = binding.characterRecycler //findViewById(R.id.characterRecycler)
        val adapter = CharactersAdapter(arrayListOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        refresh.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            errorMsg.visibility = View.GONE
            recyclerView.visibility = View.GONE

            characterViewModel.refreshFromNetwork()
            refresh.isRefreshing = false
        }

        characterViewModel.charMutableLiveData.observe(this){
            it?.let {
                recyclerView.visibility = View.VISIBLE
                adapter.setList(it as ArrayList<CharactersModelItem>) //= it as ArrayList<CharactersModelItem>
            }
        }
        characterViewModel.errorMessage.observe(this){
            it?.let {
                if (it) {
                    errorMsg.visibility = View.VISIBLE
                }
                else
                    errorMsg.visibility = View.GONE
            }
        }
        characterViewModel.progressBar.observe(this) {
            it?.let {
                if (it) {
                    recyclerView.visibility = View.GONE
                    errorMsg.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}