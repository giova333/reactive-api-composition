package com.gladunalexander.twitter.comments

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import reactor.blockhound.BlockHound

@SpringBootApplication
class CommentsApplication

fun main(args: Array<String>) {
    BlockHound.install()

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
