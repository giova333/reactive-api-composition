package com.gladunalexander.twitter.posts

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface PostRepository : ReactiveMongoRepository<Post, String> {

    fun findByUserId(userId: String): Flux<Post>
}