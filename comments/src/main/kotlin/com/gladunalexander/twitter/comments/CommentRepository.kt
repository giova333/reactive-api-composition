package com.gladunalexander.twitter.comments

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface CommentRepository : ReactiveMongoRepository<Comment, String> {

    fun findByPostId(postId: String): Flux<Comment>
}