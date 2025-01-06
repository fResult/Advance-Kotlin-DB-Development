import lessons.lesson03
import org.jetbrains.exposed.sql.Database

fun main() {
  println("Hello Exposed!")
  connect()

  // lesson02()

  lesson03()
}

fun connect() = Database.connect(
  url = "jdbc:postgresql://localhost:5431/sports_db_populated",
  user = "sports_db_admin",
  password = "12345678"
)
