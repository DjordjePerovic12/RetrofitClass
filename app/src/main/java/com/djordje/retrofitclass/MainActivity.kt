package com.djordje.retrofitclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.djordje.retrofitclass.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


const val TAG = "MainActivty"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        lifecycleScope.launch {
            binding.progressBar.isVisible = true

            val response = try {
                RetrofitInstance.api.getTodos()
            } catch (e: IOException) {
                binding.progressBar.isVisible = false
                Log.d(TAG, "IOException, check internet connection")
                return@launch
            } catch (e: HttpException) {
                binding.progressBar.isVisible = false
                Log.d(TAG, "HttpException, unexpected response")
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                todoAdapter.todos = response.body()!!
            } else {
                Log.d(TAG, "Response not successful")
            }
            binding.progressBar.isVisible = false

        }
    }


    private fun setUpRecyclerView() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}