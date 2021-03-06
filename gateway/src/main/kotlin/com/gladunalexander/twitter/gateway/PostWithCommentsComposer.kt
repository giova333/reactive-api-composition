package com.gladunalexander.twitter.gateway

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class PostWithCommentsComposer(private val lbWebClientBuilder: WebClient.Builder) {

    fun get(postId: String): Mono<PostWithComments> {
        val posts = lbWebClientBuilder
                .build()
                .get()
                .uri("http://posts/posts/{postId}", postId)
                .retrieve()
                .bodyToMono(Post::class.java)
                .subscribeOn(Schedulers.elastic())

        val comments = lbWebClientBuilder
                .build()
                .get()
                .uri("http://comments/comments/post/{postId}", postId)
                .retrieve()
                .bodyToFlux(Comment::class.java)
                .collectList()
                .subscribeOn(Schedulers.elastic())

        return Mono.zip(posts, comments, PostWithComments.Companion::from)
    }

}