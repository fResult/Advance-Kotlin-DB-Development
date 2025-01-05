import enumerations.OrderStatus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import tables.CustomersTable
import tables.OrdersTable
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
  println("Hello Exposed!")
  connect()

  transaction {
    SchemaUtils.createMissingTablesAndColumns(CustomersTable, OrdersTable)

    // 02_02
    // do0202()

    // 02_03
    // do0203()

    // 02_04
    // do0204()

    // 02_05
    // do0205()

    // 02_06
    // do0206()

    // 02_07
    // do0207()

    // 02_08
    SchemaUtils.addMissingColumnsStatements(OrdersTable)
    OrdersTable.insert { row ->
      row[totalDue] = "50.50"
      row[customerId] = 1001
      row[status] = OrderStatus.Created
    }
  }
}

fun do0207() {
  SchemaUtils.drop(OrdersTable)
  SchemaUtils.create(OrdersTable)

  OrdersTable.insert { row ->
    row[totalDue] = "376.86"
    row[customerId] = 789
    row[status] = OrderStatus.Created
  }
  OrdersTable.insert { row ->
    row[totalDue] = "499.99"
    row[customerId] = 555
    row[status] = OrderStatus.PastDue
  }
}

fun do0206() {
  CustomersTable.selectAll()
    .orderBy(CustomersTable.lastName to SortOrder.DESC)
    .limit(10, offset = 20)
    .forEach(::println)
}

fun do0205() {
  CustomersTable.insert { row ->
    row[firstName] = "John"
    row[lastName] = "Carter"
    row[email] = "mcooper@go.com"
  }
}

fun do0204() {
  val customerAmount = 500_000
  // /* Uncomment to create tons of customers with Orders only one time for testing */
  // // createCustomersWithOrders(customerAmount, orderAmountPerCustomer = 1)

  // We can try to see the difference of time to execute between indexes and no indexes at `orders.customer_id`
  val timeTook = measureTimeMillis {
    val resultRow = OrdersTable.selectAll().where { OrdersTable.customerId eq (customerAmount / 2).toLong() }.first()
    println(resultRow[OrdersTable.customerId])
  }

  println("Time to execute: $timeTook ms")
}

fun createCustomersWithOrders(customerAmount: Int, orderAmountPerCustomer: Int) = transaction {
  var totalRows = 0L
  val percent = (customerAmount * orderAmountPerCustomer) / 100

  repeat(customerAmount) { customerAmountIdx ->
    val newCustomerId = CustomersTable.insertAndGetId { row ->
      row[firstName] = customerAmountIdx.toString()
      row[lastName] = customerAmountIdx.toString()
    }

    repeat(orderAmountPerCustomer) {
      OrdersTable.insert { row ->
        row[totalDue] = Random.nextInt(0, 100).toString()
        row[customerId] = newCustomerId.value
      }
    }

    totalRows++

    if (totalRows % percent == 0L) {
      println("${customerAmountIdx / percent}%")
      commit()
    }
  }
}

fun do0203() {
  OrdersTable.insert { row ->
    row[customerId] = 100
    row[totalDue] = "10$"
  }
  CustomersTable.deleteWhere { CustomersTable.id eq 101L }
}

fun do0202() {
  val capitalizedFirstName = CustomersTable.firstName.function("UPPER").alias("capitalized_first_name")
  CustomersTable.select(CustomersTable.id, capitalizedFirstName)
    .forEach { row ->
      println("${row[CustomersTable.id]}: ${row[capitalizedFirstName]}")
    }
}

fun connect() = Database.connect(
  url = "jdbc:postgresql://localhost:5431/sports_db_populated",
  user = "sports_db_admin",
  password = "12345678"
)
