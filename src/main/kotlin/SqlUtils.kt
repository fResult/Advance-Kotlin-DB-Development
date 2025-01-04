import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Function
import org.jetbrains.exposed.sql.SqlExpressionBuilder.concat
import org.jetbrains.exposed.sql.stringLiteral

object SqlUtils {

  fun buildEmail(name: Column<String>): Function<String> =
    concat(name, stringLiteral("@"), stringLiteral("example.com"))
}