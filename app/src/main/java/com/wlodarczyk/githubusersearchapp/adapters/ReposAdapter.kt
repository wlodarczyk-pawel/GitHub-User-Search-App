package com.wlodarczyk.githubusersearchapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wlodarczyk.githubusersearchapp.R
import com.wlodarczyk.githubusersearchapp.databinding.RvUserReposBinding
import com.wlodarczyk.githubusersearchapp.network.UserRepos

class ReposAdapter(var userRepos: List<UserRepos>) :
    RecyclerView.Adapter<ReposAdapter.MainViewHolder>() {

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RvUserReposBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bindData(userRepos: UserRepos) {

            val url: String = userRepos.html_url

            binding.open.text = "OPEN"
            binding.open.setOnClickListener {

                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                itemView.context.startActivity(webIntent)
            }

            binding.repoName.text = userRepos.name
            binding.description.text = userRepos.description
            binding.createdAt.text = "Created at " + userRepos.created_at
            binding.updatedAt.text = "Updated at " + userRepos.updated_at
            binding.size.text = "Size: " + userRepos.size
            binding.starsCount.text = "Stars: " + userRepos.stargazers_count
            binding.watchersCount.text = "Watchers: " + userRepos.watchers_count
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_user_repos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val currentItem = userRepos[position]
        holder.bindData(currentItem)
    }

    override fun getItemCount(): Int {
        return userRepos.size
    }

}


