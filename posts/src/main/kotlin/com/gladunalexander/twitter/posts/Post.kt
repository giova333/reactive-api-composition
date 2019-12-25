package com.gladunalexander.twitter.posts

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("posts")
data class Post(
        @Id
        val id: String? = null,
        val userId: String,
        val text: String,
        val topic: String)