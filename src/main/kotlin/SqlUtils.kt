import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Function
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.stringLiteral

object SqlUtils {

  fun buildEmail(name: Column<String>): Function<String> =
    with(SqlExpressionBuilder) {
      concat(name, stringLiteral("@"), stringLiteral("example.com"))
    }
}