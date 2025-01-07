import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tables.CustomersTable
import java.sql.ResultSet
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CustomerTableTest {
  private lateinit var db: Database

  @BeforeEach
  fun setUp() {
    db = Database.connect(
      "jdbc:postgresql://localhost:5431/sports_db_test",
      user = "sports_db_admin",
      password = "12345678",
    )

    transaction { SchemaUtils.create(CustomersTable) }
  }

  @AfterEach
  fun cleanUp() {
    transaction { SchemaUtils.drop(CustomersTable) }

    db.connector().close()
  }

  @Test
  fun `insert creates a new row in Customers`(): Unit = transaction {
    val newId = CustomersTable.insertAndGetId { row ->
      row[firstName] = "John"
      row[lastName] = "Wick"
      row[city] = "New York"
      row[state] = "NY"
    }

    val query = CustomersTable.selectAll()
      .where(CustomersTable.id eq newId)

    val statement = query.prepareSQL(this)
    println(statement)

    val row = query.firstOrNull()

    assertNotNull(row)
    assertEquals("John", row[CustomersTable.firstName])
  }
}
