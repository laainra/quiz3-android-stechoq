package com.lailaar.quiz3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lailaar.quiz3.R
import com.lailaar.quiz3.data.remote.ApiClient
import com.lailaar.quiz3.data.remote.ApiService
import com.lailaar.quiz3.ui.adapter.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiService = ApiClient.retrofit.create(ApiService::class.java)

        setupRecyclerView()
        fetchData()
    }

    private fun setupRecyclerView() {
        adapter = PhotoAdapter(emptyList())
        rvPhotos.layoutManager = LinearLayoutManager(this)
        rvPhotos.adapter = adapter
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getPhotos()
                withContext(Dispatchers.Main) {
                    adapter = PhotoAdapter(response)
                    rvPhotos.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
