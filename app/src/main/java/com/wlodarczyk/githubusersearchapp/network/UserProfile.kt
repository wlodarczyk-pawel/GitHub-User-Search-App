package com.wlodarczyk.githubusersearchapp.network

import com.squareup.moshi.Json

data class UserProfile (

    @Json(name = "login")
    val login : String,
    @Json(name = "avatar_url")
    val avatar_url : String,
    @Json(name = "location")
    val location : String?,
    @Json(name = "bio")
    val bio : String?,
    @Json(name = "email")
    val email : String?,
    @Json(name = "public_repos")
    val public_repos : String,
    @Json(name = "repos_url")
    val repos_url : String,
    @Json(name = "name")
    val name : String
)

data class UserProfileList (
    val result: List<UserProfile>
)