package functional.transaction

import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class TransactionService {

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private var transactions = mutableListOf(
        Transaction("a1b2"),
        Transaction("b2c3"),
        Transaction("c3d4")
    )

    private var openTransactions = mutableListOf(
        Transaction("t4t5"),
        Transaction("l4o2"),
        Transaction("p5g9")
    )

    fun create() : Mono<Transaction> {
        print(transactions)
        val randomString = (1..5)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        openTransactions.add(Transaction(randomString))
        print(openTransactions)
        return Mono.just(Transaction(randomString))
    }

    fun rollback(transactionId: String) : Mono<ServerResponse> {
        // Transaction deleted
        return if (transactionId in transactions.map { it.transactionId }) {
            transactions = transactions.filterNot { it.transactionId == transactionId }.toMutableList()
            print(transactions)
            ServerResponse.status(HttpStatus.NO_CONTENT).build()
        } else {
            // Transaction not found
            ServerResponse.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
        }
    }

    fun commit(transactionId: String): Mono<ServerResponse> {
        return if (transactionId in openTransactions.map { it.transactionId }) {
            openTransactions = openTransactions.filterNot { it.transactionId == transactionId }.toMutableList()
            ServerResponse.status(HttpStatus.NO_CONTENT).build()
        } else {
            ServerResponse.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
        }
    }
}