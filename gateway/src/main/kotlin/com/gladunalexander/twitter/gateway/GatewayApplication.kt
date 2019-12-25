package com.gladunalexander.twitter.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.support.beans
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import org.springframework.web.server.WebFilter

@SpringBootApplication
@EnableDiscoveryClient
class GatewayApplication {

    //Declared here because lb does not work when initialized using kotlin dsl
    @Bean
    @LoadBalanced
    fun loadBalancedClient(): WebClient.Builder = WebClient.builder()
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args) {
        addInitializers(beans {
            bean<PostWithCommentsComposer>()

            bean {
                config(ref())
            }

            bean {
                api(ref())
            }

            bean {
                restClientNotFountFilter()
            }
        })
    }
}

fun config(builder: RouteLocatorBuilder) = builder.routes {
    route("posts-service") {
        path("/posts/**")
        uri("lb://posts")
    }
    route("comments-service") {
        path("/comments/**")
        uri("lb://comments")
    }

}

fun api(postWithCommentsComposer: PostWithCommentsComposer) = router {
    "/gateway/posts".nest {
        GET("/{postId}") {
            ServerResponse.ok().body(
                    postWithCommentsComposer.get(it.pathVariable("postId")), Post::class.java)
        }
    }
}

fun restClientNotFountFilter(): WebFilter = WebFilter { exchange, next ->
    next.filter(exchange)
            .onErrorResume(WebClientResponseException.NotFound::class.java) {
                exchange.response.statusCode = HttpStatus.NOT_FOUND
                exchange.response.setComplete()
            }
}
