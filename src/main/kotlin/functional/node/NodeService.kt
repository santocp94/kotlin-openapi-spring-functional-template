package functional.node

import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class NodeService {

    private var nodes = mutableListOf<Node>(
        Node("N1", "First", "Object", mapOf("key" to 1)),
        Node("N2", "Second", "Object", mapOf("key" to 2))
    )

    fun createNode(inputNode: Node) : Mono<Node> {
        nodes.add(inputNode)
        print("\n")
        print(inputNode.toString())
        return Mono.just(inputNode)
    }

    fun createNodeInTransaction(inputNode: Mono<Node>): Mono<Node> {
        print("\n")
        print(inputNode.toString())
        nodes.add(inputNode.blockOptional().orElse(nodes[0]))
        return Mono.just(inputNode.blockOptional().orElse(nodes[0]))
    }

    //todo document 204 response
    fun getNodeByName(nodeName: String) : Mono<ServerResponse> {
        print(nodeName)
        return if (nodeName in nodes.map { it.name }) {
            ServerResponse.status(HttpStatus.OK).bodyValue(nodes.filter { it.name == nodeName }.first())
        } else {
            ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(nodes[0])
        }
    }

    fun getNodeById(nodeId: String) : Mono<ServerResponse> {
        print(nodeId)
        return if (nodeId in nodes.map { it.nodeId }) {
            ServerResponse.status(HttpStatus.OK).bodyValue(nodes.filter { it.nodeId == nodeId }.first())
        } else {
            ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(nodes[0])
        }
    }
}