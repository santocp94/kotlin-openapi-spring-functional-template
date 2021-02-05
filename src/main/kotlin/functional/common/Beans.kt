package functional.common

import functional.Routes
import functional.node.NodeHandler
import functional.node.NodeService
import functional.transaction.TransactionHandler
import functional.transaction.TransactionService
import functional.users.UserHandler
import functional.users.UserService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.server.adapter.WebHttpHandlerBuilder

fun beans() = beans {
    //bean<TransactionHandler>()
    //bean<TransactionService>()
    bean<NodeHandler>()
    bean<NodeService>()
    bean<Routes>()
    bean(WebHttpHandlerBuilder.WEB_HANDLER_BEAN_NAME) {
        RouterFunctions.toWebHandler(ref<Routes>().router())
    }
}
