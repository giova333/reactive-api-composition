package com.gladunalexander.twitter.comments

import org.springframework.web.reactive.function.server.router

class CommentRoutes(private val handler: CommentHandler) {

    fun routes() = router {
        "/comments".nest {
            GET("/post/{postId}", handler::getPostComments)
            POST("/", handler::save)
        }
    }

}