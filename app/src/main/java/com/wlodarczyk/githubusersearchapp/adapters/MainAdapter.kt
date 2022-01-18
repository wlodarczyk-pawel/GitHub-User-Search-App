package com.wlodarczyk.githubusersearchapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.wlodarczyk.githubusersearchapp.R
import com.wlodarczyk.githubusersearchapp.databinding.RvItemBinding
import com.wlodarczyk.githubusersearchapp.network.UserProfile
import com.wlodarczyk.githubusersearchapp.ui.OnRepoButtonListener

class MainAdapter(val userList: List<UserProfile>, val onRepoButtonListener: OnRepoButtonListener) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = RvItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindData(userProfile: UserProfile) {

            binding.repos.text = "SHOW REPOS"
            binding.name.text = userProfile.name
            binding.login.text = "Login: " + userProfile.login
            binding.location.text = "Location: " + userProfile.location
            binding.email.text = "Email: " + userProfile.email
            binding.bio.text = "Bio: " + userProfile.bio
            binding.publicRepos.text = "Repositories: " + userProfile.public_repos

            binding.avatar.load(userProfile.avatar_url) {
                crossfade(700)
                transformations(CircleCropTransformation())
                scale(coil.size.Scale.FILL)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.bindData(currentItem)
        with(holder) {
            binding.repos.setOnClickListener {
                onRepoButtonListener.onRepoButton()
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}


