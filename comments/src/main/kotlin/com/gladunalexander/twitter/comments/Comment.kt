package com.gladunalexander.twitter.comments

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Comment(
        @Id
        val id: String? = null,
        val userId: String,
        val postId: String,
        val text: String)
