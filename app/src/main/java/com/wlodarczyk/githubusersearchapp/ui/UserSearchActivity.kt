package com.wlodarczyk.githubusersearchapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wlodarczyk.githubusersearchapp.adapters.MainAdapter
import com.wlodarczyk.githubusersearchapp.adapters.ReposAdapter
import com.wlodarczyk.githubusersearchapp.databinding.ActivityUserSearchBinding
import com.wlodarczyk.githubusersearchapp.network.ApiClient
import com.wlodarczyk.githubusersearchapp.network.UserProfile
import com.wlodarczyk.githubusersearchapp.network.UserRepos
import retrofit2.Call
import retrofit2.Response

class UserSearchActivity: AppCompatActivity(), OnRepoButtonListener {

    private lateinit var binding: ActivityUserSearchBinding

    var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonSearch.setOnClickListener {
            clearRecyclerView()
            fetchUsers()
            binding.buttonSearch.hideKeyboard()
        }

    }

    override fun onRepoButton() {
        fetchRepos(userName)
    }

    private fun clearRecyclerView() {
        binding.reposRecyclerView.adapter = null
    }

    private fun fetchUsers() {

        userName = binding.enterUserName.text.toString()

        if (userName.trim().isNotEmpty()) {

            val client = ApiClient.apiService.fetchUserProfiles(userName)
            client.enqueue(object : retrofit2.Callback<UserProfile> {

                override fun onResponse(
                    call: Call<UserProfile>, response: Response<UserProfile>
                ) {
                    handleResponse(response)
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Log.d("onFailure: ", "failed " + t.message)
                    Toast.makeText(
                        this@UserSearchActivity,
                        "User don't exist! Make sure you're provide correct user name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        } else {
            Toast.makeText(this@UserSearchActivity, "Enter User Name!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleResponse(response: Response<UserProfile>) {

        if (response.isSuccessful) {

            val result = listOf(response.body())
            result.let {
                val adapter = MainAdapter(result as List<UserProfile>, this)
                binding.usersRecyclerView
                binding.usersRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
                binding.usersRecyclerView.adapter = adapter
            }
        }
    }

    private fun fetchRepos(userName: String) {

        if (userName.trim().isNotEmpty()) {

            val client = ApiClient.apiService.fetchUserRepos(userName)
            client.enqueue(object : retrofit2.Callback<List<UserRepos>> {

                override fun onResponse(
                    call: Call<List<UserRepos>>, response: Response<List<UserRepos>>
                ) {
                    handleResponseRepos(response)
                }

                override fun onFailure(call: Call<List<UserRepos>>, t: Throwable) {
                    Log.d("onFailureRepos: ", "failed " + t.message)
                }
            })

        } else {
            Toast.makeText(this@UserSearchActivity, "Enter User Name!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleResponseRepos(response: Response<List<UserRepos>>) {

        if (response.isSuccessful) {

            val result = response.body()
            result?.let {
                val adapter = ReposAdapter(result)
                binding.reposRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                binding.reposRecyclerView.adapter = adapter
            }
        }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}

interface OnRepoButtonListener {
    fun onRepoButton()
}

