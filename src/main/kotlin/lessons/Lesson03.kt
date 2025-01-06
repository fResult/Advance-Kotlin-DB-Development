package lessons

import enumerations.OrderStatus
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.transactions.transaction
import tables.CustomersTable
import tables.OrdersTable

fun lesson03() {
  transaction {
    val city = CustomersTable.city
    val cityCount = city.count()

    (OrdersTable innerJoin CustomersTable)
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
      .forEach(::println)
  }
}
