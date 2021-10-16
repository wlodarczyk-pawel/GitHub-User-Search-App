package com.wlodarczyk.githubusersearchapp.network

import com.squareup.moshi.Json

data class UserRepos(

    @Json(name = "name")
    val name: String,
    @Json(name = "html_url")
    val html_url: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "created_at")
    val created_at: String?,
    @Json(name = "updated_at")
    val updated_at: String?,
    @Json(name = "size")
    val size: String,
    @Json(name = "stargazers_count")
    val stargazers_count: String,
    @Json(name = "watchers_count")
    val watchers_count: String,


)
