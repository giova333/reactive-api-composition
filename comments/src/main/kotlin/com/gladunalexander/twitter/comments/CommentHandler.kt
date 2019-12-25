package com.gladunalexander.twitter.comments

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class CommentHandler(private val repository: CommentRepository) {

    fun getPostComments(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(
                    repository.findByPostId(request.pathVariable("postId")), Comment::class.java)

    fun save(request: ServerRequest): Mono<ServerResponse> =
            ServerResponse.ok().body(
                    request.bodyToMono(NewComment::class.java)
                            .map { Comment(userId = it.userId, text = it.text, postId = it.postId) }
                            .flatMap { repository.save(it) },
                    Comment::class.java)
}