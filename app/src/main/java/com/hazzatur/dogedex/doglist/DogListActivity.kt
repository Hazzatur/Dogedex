package com.hazzatur.dogedex.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazzatur.dogedex.api.ApiResponseStatus
import com.hazzatur.dogedex.databinding.ActivityDogListBinding
import com.hazzatur.dogedex.dogdetail.DogDetailActivity
import com.hazzatur.dogedex.dogdetail.DogDetailActivity.Companion.DOG_KEY

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.dogRecycler
        recycler.layoutManager = LinearLayoutManager(this)

        val loadingWheel = binding.loadingWheel

        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }
        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this) { dogList ->
            adapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Loading -> loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> loadingWheel.visibility = View.GONE
                is ApiResponseStatus.Error -> {
                    Toast.makeText(
                        this,
                        status.messageId,
                        Toast.LENGTH_SHORT
                    ).show()
                    loadingWheel.visibility = View.GONE
                }
            }
        }
    }
}