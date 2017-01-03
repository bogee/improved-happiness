/*
 */

package dal

import slick.driver.PostgresDriver.api._

trait MappedColumnTypes {

  implicit val instantToSqlTimestamp = MappedColumnType.base[java.time.Instant, java.sql.Timestamp](
    { instant => java.sql.Timestamp.from(instant) },
    { timestamp => timestamp.toInstant }
  )

}
