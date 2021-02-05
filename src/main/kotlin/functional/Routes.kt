package functional

import functional.common.internalServerError
import functional.node.NodeHandler
import functional.transaction.TransactionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.router


class Routes(/*private val transactionHandler: TransactionHandler, */private val nodeHandler: NodeHandler) {

    fun router() = router {
        accept(TEXT_HTML).nest {
            GET("/") { permanentRedirect(URI("index.html")).build() }
        }
        "/api".nest {
            accept(APPLICATION_JSON).nest {

                /** TRANSACTION routes */
                //POST("/transaction/commit/{trans_id}", transactionHandler::commit)
                //DELETE("/transaction/{trans_id}", transactionHandler::rollback)
                //POST("/transaction", transactionHandler::create)

                /** NODE routes */
                GET("/node/name/{name}", nodeHandler::getNodeByName)
                GET("/node/id/{node_id}",  nodeHandler::getNodeById)
                POST("/node",  nodeHandler::createNode)
                POST("/node/{trans_id}",  nodeHandler::createNodeInTransaction)

                /** RELATIONSHIP routes */
                //GET("/relationship/id/{rel_id}")
                //POST("/relationship")
                //POST("/relationship/{trans_id}")

                /** SEARCH routes */
                //POST("/search")

                /** PROVENANCE routes */
                //POST("/prov/forward/{node_id}/{step}")
                //POST("/prov/backward/{node_id}/{step}")

                /** ORCHESTRATION routes */
                //GET("/orchestration/operation_type/{node_id}")

                /** METADATA routes */
                //POST("/metadata/object")
                //POST("/metadata/schema")
                //POST("/metadata/operation")



            }
        }
        resources("/**", ClassPathResource("static/"))
    }
    .filter { request, next ->
        try {
            next.handle(request)
        } catch (ex: Exception) {
            internalServerError(ex)
        }
    }
}