import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
  connect()

  transaction {
    SchemaUtils.createMissingTablesAndColumns(OrdersTable)

    CustomersTable.deleteWhere { CustomersTable.id eq 100 }
  }
}

object CustomersTable : IntIdTable() {
  val firstName = varchar("first_name", 20)
  val lastName = varchar("last_name", 20)
  val email = varchar("email", 50).nullable()
}

private object OrdersTable : IntIdTable() {
  val totalDue = varchar("total_due", 10)
  val customerId = integer("customer_id").references(CustomersTable.id, onDelete = ReferenceOption.CASCADE)
}

fun connect() {
  Database.connect(
    "jdbc:postgresql://localhost:5431/sports_db_populated",
    user = "sports_db_admin",
    password = "12345678"
  )
}

