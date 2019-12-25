package com.gladunalexander.twitter.comments

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans

@SpringBootApplication
class CommentsApplication

fun main(args: Array<String>) {
    runApplication<CommentsApplication>(*args) {
        addInitializers(
                beans {
                    bean<CommentHandler>()

                    bean {
                        CommentRoutes(ref()).routes()
                    }
                }
        )
    }
}
