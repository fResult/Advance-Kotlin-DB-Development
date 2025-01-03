import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
  println("Hello Exposed!")
  connect()

  transaction {
    recreateTables()

    val statement = CustomersTable.insert { row ->
      row[name] = "Alice"
    }

    val newId = CustomersTable.insertAndGetId { row ->
      row[name] = "Bob"
    }

    val newRow = CustomersTable.insert { row -> row[name] = "Korn" }.resultedValues?.first()

    statement.resultedValues?.singleOrNull()?.also { println("New row: ${it.getOrNull(CustomersTable.name)}") }
    println("Inserted row with id: $newId")
    println("New row: $newRow")
  }
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
