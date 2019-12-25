package com.gladunalexander.twitter.posts

import org.springframework.web.reactive.function.server.router

class PostRoutes(private val handler: PostHandler) {

    fun routes() = router {
        "/posts".nest {
            GET("/user/{userId}", handler::getUserPosts)
            GET("/{postId}", handler::getById)
            POST("/", handler::save)
        }
    }

}