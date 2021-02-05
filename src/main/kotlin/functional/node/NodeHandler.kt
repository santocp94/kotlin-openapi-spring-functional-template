package functional.node

import functional.common.validate
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.body

class NodeHandler(val nodeService: NodeService) {

    fun getNodeByName(req: ServerRequest) = validate
        .request(req){
            nodeService.getNodeByName(req.pathVariable("name"))
        }

    fun getNodeById(req: ServerRequest) = validate
        .request(req){
            nodeService.getNodeById(req.pathVariable("node_id"))
        }

    fun createNode(req: ServerRequest) = validate
        .request(req){
            ok().body(nodeService.createNode(Node("","","", mapOf())))
        }
    //todo how to parse the body? insert path param too
    fun createNodeInTransaction(req: ServerRequest) = validate
        .request(req){
            ok().body(nodeService.createNodeInTransaction(req.bodyToMono()))
        }
}