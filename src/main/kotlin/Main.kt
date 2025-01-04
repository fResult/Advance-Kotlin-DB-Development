import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.transaction
import tables.CustomersTable
import tables.OrdersTable
import java.math.BigDecimal

fun main() {
  println("Hello Exposed!")
  connect()

  transaction {
    CustomersTable.slice(CustomersTable.id, CustomersTable.firstName)
      .selectAll()
      .forEach(::println)
  }
}

fun connect() = Database.connect(
  url = "jdbc:postgresql://localhost:5431/sports_db_populated",
  user = "sports_db_admin",
  password = "12345678"
)
