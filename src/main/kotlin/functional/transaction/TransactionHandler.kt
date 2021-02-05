package functional.transaction

import functional.common.validate
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

class TransactionHandler(val transactionService: TransactionService) {

    fun rollback(req: ServerRequest) = validate
        .request(req){
            //ok().body(transactionService.rollback(req.queryParam("trans_id").orElse("empty")))
            //status(HttpStatus.NO_CONTENT).build()
            transactionService.rollback(req.pathVariable("trans_id"))
        }/*
        .withBody(Transaction::class.java) { transaction ->
            print(req)
            val id = req.pathVariable("trans_id")
            print(id)
            ok().body(transactionService.rollback(transaction.transactionId))
        }
        */

    fun create(req: ServerRequest) = validate
        .request(req) {
            ok().body(transactionService.create())
        }

    fun commit(req: ServerRequest) = validate
        .request(req) {
            ok().body(transactionService.commit(req.pathVariable("trans_id")))
        }
}