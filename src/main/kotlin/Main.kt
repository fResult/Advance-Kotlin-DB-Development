import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
  println("Hello Exposed!")
  val db = Database.connect(
    url = "jdbc:postgresql://localhost:5432/sports_db",
    user = "sports_db_admin",
    password = "12345678"
  )

  transaction {
    SchemaUtils.drop(CustomersTable)
    SchemaUtils.create(CustomersTable)
  }
}

object CustomersTable : LongIdTable("customers") {
  val name = varchar("name", 20)
  val email = varchar("email", 50).nullable()
}
