import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
  println("Hello Exposed!")
  val db = Database.connect(
    "jdbc:postgresql://localhost:5432/sports_db",
    user = "sports_db_admin",
    password = "12345678"
  )

  transaction {

  }
}