import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

fun main() {
  connect()

  transaction {
    recreateTables()

    OrdersTable.insert { it[totalDue] = BigDecimal(140.91); it[status] = "Paid" }
    OrdersTable.insert { it[totalDue] = BigDecimal(105.32); it[status] = "Returned" }
    OrdersTable.insert { it[totalDue] = BigDecimal(217.30); it[status] = "Past Due" }
    OrdersTable.insert { it[totalDue] = BigDecimal(281.39); it[status] = "Paid" }

    OrdersTable.update({ OrdersTable.status eq "Returned" }) {
      it[totalDue] = BigDecimal(246.12)
    }

    OrdersTable.deleteWhere { OrdersTable.totalDue less BigDecimal(200) }

    OrdersTable.select { (OrdersTable.status eq "Paid") and (OrdersTable.totalDue greaterEq BigDecimal(200)) }
      .forEach(::println)
  }
}

fun recreateTables() = transaction {
  SchemaUtils.drop(CustomersTable, OrdersTable)
  SchemaUtils.create(CustomersTable, OrdersTable)
}

fun connect() {
  Database.connect(
    "jdbc:postgresql://localhost:5432/sports_db",
    user = "sports_db_admin",
    password = "12345678"
  )
}

object CustomersTable : IntIdTable() {
  val name = varchar("name", 20)
  val email = varchar("email", 50).nullable()
}

object OrdersTable : LongIdTable() {
  val totalDue = decimal("total_due", 5, 2)
  val status = varchar("status", 20)
}
