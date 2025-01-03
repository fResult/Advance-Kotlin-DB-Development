import org.jetbrains.exposed.sql.Database

fun main() {
  println("Hello Exposed!")
  val db = Database.connect(
    url = "jdbc:postgresql://localhost:5432/sports_db",
    user = "sports_db_admin",
    password = "12345678"
  )

  println("${db.vendor} ${db.dialect.name}")
}