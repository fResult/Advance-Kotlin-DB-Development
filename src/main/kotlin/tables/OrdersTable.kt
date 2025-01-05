package tables

import enumerations.OrderStatus
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object OrdersTable : LongIdTable("orders") {
  val totalDue = varchar("total_due", 10)
  val customerId = long("customer_id")
    .references(CustomersTable.id, onDelete = ReferenceOption.CASCADE)
    .index()
  val status = enumerationByName<OrderStatus>("status", 20)
}
