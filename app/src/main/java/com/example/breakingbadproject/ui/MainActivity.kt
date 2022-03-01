package com.example.breakingbadproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.breakingbadproject.R
import com.example.breakingbadproject.databinding.ActivityMainBinding
import com.example.breakingbadproject.model.CharactersModelItem
import com.example.breakingbadproject.viewmodel.CharacterViewModel
import java.util.zip.Inflater

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //lateinit var apiService: ApiService
    //lateinit var charList: MutableList<CharactersModelItem>

    lateinit var progressBar : ProgressBar
    lateinit var errorMsg : TextView
    lateinit var refresh : SwipeRefreshLayout

    private lateinit var characterViewModel : CharacterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        supportActionBar?.hide()

        characterViewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)

        characterViewModel.getCharacters()

        progressBar = binding.progressCircular
        errorMsg = binding.errorMessage
        refresh = binding.swipeRefresh

        val recyclerView : RecyclerView = binding.characterRecycler //findViewById(R.id.characterRecycler)
        val adapter : CharactersAdapter = CharactersAdapter(arrayListOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        refresh.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            errorMsg.visibility = View.GONE
            recyclerView.visibility = View.GONE

            characterViewModel.getCharacters()
            refresh.isRefreshing = false
        }

        characterViewModel.charMutableLiveData.observe(this, Observer<List<CharactersModelItem>>(){
            it?.let {
                recyclerView.visibility = View.VISIBLE
                adapter.setList(it as ArrayList<CharactersModelItem>) //= it as ArrayList<CharactersModelItem>
            }
        })
        characterViewModel.errorMessage.observe(this, Observer {
            it?.let {
                if (it) {
                    errorMsg.visibility = View.VISIBLE
//                    recyclerView.visibility = View.GONE
//                    progressBar.visibility = View.GONE
                }
                else
                    errorMsg.visibility = View.GONE
            }
        })
        characterViewModel.progressBar.observe(this, Observer {
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
        })

//        postViewModel.postsMutableLiveData.observe(this,
//            Observer<List<Any?>?> { postModels -> adapter.setList(postModels) })
//





        //apiService = ApiClient.getClient().create(ApiService::class.java)
        //var post = apiService.characterList()

//        post.enqueue(object : Callback<List<CharactersModelItem>> {
//            override fun onFailure(call: Call<List<CharactersModelItem>>, t: Throwable) {
//                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<List<CharactersModelItem>>, response: Response<List<CharactersModelItem>>) {
//
//                if (response.isSuccessful) {
//                    charList = (response.body() as MutableList<CharactersModelItem>?)!!
//                }
//            }
//        })
//        var character = apiService.characterList()
//
//        character.enqueue(object : Callback<List<CharactersModelItem>>{
//            override fun onResponse(
//                call: Call<List<CharactersModelItem>>,
//                response: Response<List<CharactersModelItem>>
//            ) {
//                if (response.isSuccessful) {
//                    charList = (response.body() as MutableList<CharactersModelItem>)
//                }
//            }
//
//            override fun onFailure(call: Call<List<CharactersModelItem>>, t: Throwable) {
//                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
//            }
//
//        })
    }
}