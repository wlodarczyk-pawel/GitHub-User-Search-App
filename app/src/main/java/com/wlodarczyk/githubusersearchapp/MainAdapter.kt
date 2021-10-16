package com.wlodarczyk.githubusersearchapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.wlodarczyk.githubusersearchapp.network.UserProfile
import com.wlodarczyk.githubusersearchapp.ui.OnRepoButtonListener

class MainAdapter(val userList: List<UserProfile>, val onRepoButtonListener: OnRepoButtonListener) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var repos = itemView.findViewById<Button>(R.id.repos)

        @SuppressLint("SetTextI18n")
        fun bindData(userProfile: UserProfile) {

            val name = itemView.findViewById<TextView>(R.id.name)
            val login = itemView.findViewById<TextView>(R.id.login)
            val location = itemView.findViewById<TextView>(R.id.location)
            val email = itemView.findViewById<TextView>(R.id.email)
            val bio = itemView.findViewById<TextView>(R.id.bio)
            val public_repos = itemView.findViewById<TextView>(R.id.public_repos)
            val avatar = itemView.findViewById<ImageView>(R.id.avatar)

            repos.text = "REPOS"
            name.text = userProfile.name
            login.text = userProfile.login
            location.text = userProfile.location
            email.text = userProfile.email
            bio.text = userProfile.bio
            public_repos.text = "Repositories: " + userProfile.public_repos

            avatar.load(userProfile.avatar_url) {
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
        holder.bindData(userList[position])
        holder.repos.setOnClickListener { onRepoButtonListener.onRepoButton() }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}


