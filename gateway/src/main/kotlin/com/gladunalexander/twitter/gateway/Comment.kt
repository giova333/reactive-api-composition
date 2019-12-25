package com.gladunalexander.twitter.gateway


data class Comment(
        val id: String,
        val userId: String,
        val postId: String,
        val text: String)
