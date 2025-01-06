package tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table.Dual.nullable
import org.jetbrains.exposed.sql.Table.Dual.uniqueIndex
import org.jetbrains.exposed.sql.Table.Dual.varchar

object CustomersTable : LongIdTable("customers") {
 val firstName = varchar("first_name", 20)
  val lastName = varchar("last_name", 30)
  val email = varchar("email", 50)
    .nullable()
    .uniqueIndex()
  val city = varchar("city", 20)
  val state = varchar("state", 2)
}
