package com.gladunalexander.twitter.comments

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.support.beans
import reactor.blockhound.BlockHound

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableDiscoveryClient
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
