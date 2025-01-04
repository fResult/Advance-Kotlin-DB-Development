package tables

import org.jetbrains.exposed.dao.id.LongIdTable

object OrdersTable : LongIdTable("orders") {
  val totalDue = decimal("total_due", 5, 2)
  val status = varchar("status", 30)
}