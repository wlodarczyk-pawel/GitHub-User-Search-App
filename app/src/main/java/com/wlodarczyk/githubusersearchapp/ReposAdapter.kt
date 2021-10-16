package com.wlodarczyk.githubusersearchapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wlodarczyk.githubusersearchapp.network.UserRepos

class ReposAdapter(val userRepos: List<UserRepos>) :
    RecyclerView.Adapter<ReposAdapter.MainViewHolder>() {

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val open = itemView.findViewById<Button>(R.id.open)

        @SuppressLint("SetTextI18n")
        fun bindData(userRepos: UserRepos) {

            val repo_name = itemView.findViewById<TextView>(R.id.repo_name)
            val description = itemView.findViewById<TextView>(R.id.description)
            val created_at = itemView.findViewById<TextView>(R.id.created_at)
            val updated_at = itemView.findViewById<TextView>(R.id.updated_at)
            val size = itemView.findViewById<TextView>(R.id.size)
            val stars_count = itemView.findViewById<TextView>(R.id.stars_count)
            val watchers_count = itemView.findViewById<TextView>(R.id.watchers_count)

            val url : String = userRepos.html_url

            open.text = "OPEN"
            open.setOnClickListener {

                val webIntent: Intent = Uri.parse(url).let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                itemView.context.startActivity(webIntent)
            }

            repo_name.text = userRepos.name
            description.text = userRepos.description
            created_at.text = "Created at " + userRepos.created_at
            updated_at.text = "Updated at " + userRepos.updated_at
            size.text = "Size: " + userRepos.size
            stars_count.text = "Stars: " + userRepos.stargazers_count
            watchers_count.text = "Watchers: " + userRepos.watchers_count

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_user_repos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(userRepos[position])

    }

    override fun getItemCount(): Int {
        return userRepos.size
    }
}


