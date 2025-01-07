package lessons

import enumerations.OrderStatus
import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import tables.CustomersTable
import tables.OrdersTable

suspend fun lesson03() {
  newSuspendedTransaction {
    // 03_01
    // do0301(this)

    // 03_02
    // do0302()

    // 03_03
    // do0303()

    // 03_04
    // do0304(this)

    // Bonus: Logging long queries
    bonus(this)
  }
}

private fun bonus(txn: Transaction) {
  txn.warnLongQueriesDuration = 1

  CustomersTable.selectAll().forEach(::doNothing)
}
private fun doNothing(`_`: Any) {}

private fun do0304(txn: Transaction) {
  txn.exec("GRANT SELECT ON Customers TO public")
  txn.exec(
    "SELECT * FROM customers WHERE id = ?",
    listOf(IntegerColumnType() to 1000),
  ) { rs ->
    val results = mutableListOf<Int>()
    while (rs.next()) {
      results.add(rs.getInt("id"))
    }
    results
  }?.forEach(::println)
}

private fun do0303() {
  println("Do Nothing, it is in the unit test")
}

private suspend fun do0302() {
  doIO()
  CustomersTable.selectAll().limit(1).forEach(::println)
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

suspend fun doIO() = delay(10)
