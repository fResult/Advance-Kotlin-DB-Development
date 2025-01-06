package lessons

import org.jetbrains.exposed.sql.transactions.transaction

fun lesson03() {
  transaction {
    do0212Challenge()
  }
}
