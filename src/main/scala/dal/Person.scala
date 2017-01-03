/*
 */

package dal

import java.time.Instant

final case class Person(
  name: String,
  age: Int,
  addressUuid: String,
  uuid: Option[String] = None,
  insertedAt: Option[Instant] = None,
  updatedAt: Option[Instant] = None
)
