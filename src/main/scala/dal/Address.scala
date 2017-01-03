/*
 */

package dal

import java.time.Instant

final case class Address(
  street: String,
  city: String,
  uuid: Option[String] = None,
  insertedAt: Option[Instant] = None,
  updatedAt: Option[Instant] = None
)
