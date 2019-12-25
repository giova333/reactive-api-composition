package com.gladunalexander.twitter.posts

import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class PostHandler(private val repository: PostRepository) {

    fun getUserPosts(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(
                    repository.findByUserId(request.pathVariable("userId")), Post::class.java)

    fun getById(request: ServerRequest): Mono<ServerResponse> =
            repository.findById(request.pathVariable("postId"))
                    .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
                    .switchIfEmpty(ServerResponse.notFound().build())

    fun save(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(
                    request.bodyToMono(NewPost::class.java)
                            .map { Post(userId = it.userId, text = it.text, topic = it.topic) }
                            .flatMap { repository.save(it) },
                    Post::class.java)
}