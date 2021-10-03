package com.wlodarczyk.githubusersearchapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wlodarczyk.githubusersearchapp.MainAdapter
import com.wlodarczyk.githubusersearchapp.R
import com.wlodarczyk.githubusersearchapp.network.ApiClient
import com.wlodarczyk.githubusersearchapp.network.UserProfile
import retrofit2.Call
import retrofit2.Response

class UserSearchActivity : AppCompatActivity() {

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)

        button = findViewById(R.id.button_search)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {


                val enterUserName = findViewById(R.id.enterUserName) as EditText
                var userName : String? = enterUserName.text.toString()

                if (userName!!.trim().isNotEmpty()) {

                    val client = ApiClient.apiService.fetchUserProfiles(userName)
                    client.enqueue(object: retrofit2.Callback<UserProfile>{

                        override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>
                        ) {
                            if (response.isSuccessful){
                                Log.d("onResponse: ","success "+response.body())

                                val result = listOf(response.body())
                                result?.let {
                                    val adapter = MainAdapter(result as List<UserProfile>)
                                    val recyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
                                    recyclerView?.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
                                    recyclerView?.adapter = adapter
                                }
                            }
                        }

                        override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                            Log.d("onFailure: ","failed "+t.message)
                            Toast.makeText(this@UserSearchActivity, "User don't exist! Make sure you're provide correct user name", Toast.LENGTH_SHORT).show()

                        }
                    })

                } else {
                    Toast.makeText(this@UserSearchActivity, "Enter User Name!", Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
}