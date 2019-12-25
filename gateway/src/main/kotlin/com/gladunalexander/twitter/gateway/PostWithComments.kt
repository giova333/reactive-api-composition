package com.gladunalexander.twitter.gateway

data class PostWithComments(
        val id: String,
        val userId: String,
        val text: String,
        val topic: String,
        val comments: List<Comment>) {

    companion object {
        fun from(post: Post, comments: List<Comment>): PostWithComments =
                PostWithComments(post.id, post.userId, post.text, post.topic, comments)
    }
}