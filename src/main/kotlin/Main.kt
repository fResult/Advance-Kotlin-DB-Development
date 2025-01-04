import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.transaction


fun main() {
  println("Hello Exposed!")
  connect()

  transaction {
    recreateTables()
    createCustomers()

    trySelectCustomers()
    println("=====================================")
    tryUpdateCustomers()
  }
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

fun recreateTables() {
  SchemaUtils.drop(CustomersTable)
  SchemaUtils.create(CustomersTable)
}

fun connect() = Database.connect(
  url = "jdbc:postgresql://localhost:5432/sports_db",
  user = "sports_db_admin",
  password = "12345678"
)

object CustomersTable : LongIdTable("customers") {
  val name = varchar("name", 20)
  val email = varchar("email", 50).nullable()
}
