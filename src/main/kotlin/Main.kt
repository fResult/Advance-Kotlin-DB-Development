import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tables.CustomersTable
import tables.OrdersTable

fun main() {
  println("Hello Exposed!")
  connect()

  transaction {
    // 02_02
    // do0202()

    // 02_03
    SchemaUtils.createMissingTablesAndColumns(OrdersTable)
    OrdersTable.insert { row ->
      row[customerId] = 100
      row[totalDue] = "10$"
    }
    CustomersTable.deleteWhere { CustomersTable.id eq 101L }
  }
}

fun do0202() {
  val capitalizedFirstName = CustomersTable.firstName.function("UPPER").alias("capitalized_first_name")
  CustomersTable.slice(CustomersTable.id, capitalizedFirstName)
    .selectAll()
    .forEach { row ->
      println("${row[CustomersTable.id]}: ${row[capitalizedFirstName]}")
    }
}

fun connect() = Database.connect(
  url = "jdbc:postgresql://localhost:5431/sports_db_populated",
  user = "sports_db_admin",
  password = "12345678"
)
