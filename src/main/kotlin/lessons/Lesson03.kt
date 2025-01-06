package lessons

import enumerations.OrderStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import tables.CustomersTable
import tables.OrdersTable

suspend fun lesson03() {
  newSuspendedTransaction {
    // 03_01
    // do0301(this)

    // 03_02
    doIO()
    CustomersTable.selectAll().limit(1)
  }
}

suspend fun doIO() {
  delay(10)
}

private fun do0301(transaction: Transaction) {
  transaction.addLogger(StdOutSqlLogger)

  val city = CustomersTable.city
  val cityCount = city.count()

  val query = (OrdersTable innerJoin CustomersTable)
    .select(
      cityCount.alias("paid_order_count"),
      city,
      CustomersTable.state,
    )
    .where { OrdersTable.status eq OrderStatus.Paid }
    .limit(10)
    .groupBy(city, CustomersTable.state)
    .orderBy(
      cityCount to SortOrder.DESC,
      city to SortOrder.ASC
    )

  query.forEach(::println)
  val statement = query.prepareSQL(transaction)

  println(statement)
}

