package tables

import org.jetbrains.exposed.dao.id.LongIdTable

object CustomersTable : LongIdTable("customers") {
  val firstName = varchar("first_name", 20)
  val lastName = varchar("last_name", 30)
  val email = varchar("email", 50)
    .nullable()
    .uniqueIndex()
}
