import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
  println("Hello Exposed!")
  connect()

  transaction {
    recreateTables()
    createCustomers()

    CustomersTable.selectAll().forEach(::println)
    println("=====================================")
    CustomersTable.select { CustomersTable.email.isNotNull() }.forEach {
      println("Customer Name: ${it[CustomersTable.name]}, Email: ${it[CustomersTable.email]}")
    }
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
