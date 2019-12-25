package com.gladunalexander.twitter.comments

data class NewComment(
        val userId: String,
        val postId: String,
        val text: String
)