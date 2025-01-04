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
    recreateTables()
    createCustomers()

    trySelectCustomers()
    println("=====================================")
    tryUpdateCustomers()
    println("=====================================")
    tryDeleteCustomers()

    println("=====================================")

    doChallenge1()
  }
}

fun doChallenge1() {
  createOrders()
  OrdersTable.update({ OrdersTable.status eq "Returned" }) { row ->
    row[totalDue] = BigDecimal(246.12)
  }
  OrdersTable.deleteWhere { OrdersTable.totalDue less BigDecimal(200) }
  OrdersTable.selectAll().forEach(::println)

  println("======== Find by order with total due more than 220.0 ========")
  OrdersTable.select { (OrdersTable.status eq "Paid") and (OrdersTable.totalDue greater BigDecimal(220.0)) }
    .forEach(::println)
}

fun trySelectCustomers() {
  CustomersTable.selectAll().forEach(::println)

  println("=====================================")

  CustomersTable.select { CustomersTable.email.isNotNull() }.forEach {
    println("Customer Name: ${it[CustomersTable.name]}, Email: ${it[CustomersTable.email]}")
  }

  println("=====================================")

  CustomersTable.select { CustomersTable.name like "%o%" }.forEach(::println)

  println("=====================================")

  CustomersTable.select { CustomersTable.name inList listOf("Korn", "Alice") }.forEach(::println)

  println("=====================================")

  CustomersTable.select((CustomersTable.name.isNotNull()) and (CustomersTable.name like "%o%")).forEach(::println)
}

fun tryUpdateCustomers() {
  CustomersTable.update({ CustomersTable.id eq 1L }) { row ->
    row[email] = SqlUtils.buildEmail(name)
  }
  CustomersTable.update({ CustomersTable.name.lowerCase() eq "BoB".lowercase() }) { row ->
    row[email] = SqlUtils.buildEmail(name)
  }
  CustomersTable.selectAll().forEach(::println)
}

fun tryDeleteCustomers() {
  CustomersTable.deleteWhere { CustomersTable.id eq 2L }
  CustomersTable.selectAll().forEach(::println)

  println("=====================================")

  CustomersTable.deleteAll() // DON'T DO THIS IN PRODUCTION
  val result = CustomersTable.selectAll()
  if (result.empty()) {
    println("No customers found")
  } else {
    result.forEach(::println)
  }

}

fun createCustomers() = transaction {
  val statement = CustomersTable.insert { row ->
    row[name] = "Alice"
  }

  val newId = CustomersTable.insertAndGetId { row ->
    row[name] = "Bob"
  }

  val newRow = CustomersTable.insert { row ->
    row[name] = "Korn"
    row[email] = "korn@example.com"
  }.resultedValues?.first()
}

fun createOrders() = transaction {
  OrdersTable.insert { row ->
    row[totalDue] = BigDecimal(104.91)
    row[status] = "Paid"
  }

  OrdersTable.insert { row ->
    row[totalDue] = BigDecimal(105.32)
    row[status] = "Returned"
  }

  OrdersTable.insert { row ->
    row[totalDue] = BigDecimal(217.30)
    row[status] = "Past Due"
  }

  OrdersTable.insert { row ->
    row[totalDue] = BigDecimal(281.39)
    row[status] = "Paid"
  }
}

fun recreateTables() {
  SchemaUtils.drop(CustomersTable)
  SchemaUtils.create(CustomersTable)

  SchemaUtils.drop(OrdersTable)
  SchemaUtils.create(OrdersTable)
}

fun connect() = Database.connect(
  url = "jdbc:postgresql://localhost:5432/sports_db",
  user = "sports_db_admin",
  password = "12345678"
)
