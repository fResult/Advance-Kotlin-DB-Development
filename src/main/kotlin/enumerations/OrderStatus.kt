package enumerations

enum class OrderStatus {
  Created,
  Due,
  PastDue,
  Cancelled,
  Paid,
  Returned;

  companion object {
    fun toDb(orderStatus: OrderStatus): String {
      return when (orderStatus) {
        Created -> "created"
        Due -> "due"
        PastDue -> "past due"
        Cancelled -> "cancelled"
        Paid -> "paid"
        Returned -> "returned"
      }
    }

    fun fromDb(dbValue: Any): OrderStatus {
      return when (dbValue) {
        "created" -> Created
        "due" -> Due
        "past due" -> PastDue
        "cancelled" -> Cancelled
        "paid" -> Paid
        "returned" -> Returned
        else -> throw RuntimeException("Unknown status: $dbValue")
      }
    }
  }
}
