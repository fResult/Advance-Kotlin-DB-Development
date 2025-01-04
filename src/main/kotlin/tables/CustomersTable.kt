package tables

import org.jetbrains.exposed.dao.id.LongIdTable

object CustomersTable : LongIdTable("customers") {
  val name = varchar("name", 20)
  val email = varchar("email", 50).nullable()
}
