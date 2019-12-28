package com.gladunalexander.twitter.posts

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.support.beans
import reactor.blockhound.BlockHound

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableDiscoveryClient
class PostsApplication

fun main(args: Array<String>) {
    BlockHound.install()

    runApplication<PostsApplication>(*args) {
        addInitializers(beansConfiguration())
    }
}

fun beansConfiguration() = beans {
    bean<PostHandler>()

    bean {
        PostRoutes(ref()).routes()
    }

}