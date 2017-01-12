/*
 */
package domain

import java.time.Instant

import dal.{Address, Person}
import domain.club.{Home, Member}
import play.api.libs.json._
import play.api.libs.functional.syntax._

trait ClubFormats {

  private val addressWritesBuilder =
    (JsPath \ "street").write[String] and
    (JsPath \ "city").write[String] and
    (JsPath \ "uuid").writeNullable[String] and
    (JsPath \ "insertedAt").writeNullable[Instant] and
    (JsPath \ "updatedAt").writeNullable[Instant]

  implicit val addressWrites: Writes[Address] = addressWritesBuilder.apply(
    unlift(Address.unapply)
  )

  private val personWritesBuilder =
    (JsPath \ "name").write[String] and
    (JsPath \ "age").write[Int] and
    (JsPath \ "uuid").writeNullable[String] and
    (JsPath \ "insertedAt").writeNullable[Instant] and
    (JsPath \ "updatedAt").writeNullable[Instant]

  implicit val personWrites: Writes[Person] = personWritesBuilder.apply(
    (person: Person) => (person.name, person.age, person.uuid, person.insertedAt, person.updatedAt)
  )

  private val memberWritesBuilder =
    personWritesBuilder and
    (JsPath \ "address").write[Address]

  implicit val memberWrites: Writes[Member] = memberWritesBuilder.apply(
    (member: Member) => (
      member._1.name,
      member._1.age,
      member._1.uuid,
      member._1.insertedAt,
      member._1.updatedAt,
      member._2
    )
  )

  private val homeWritesBuilder =
    addressWritesBuilder and
    (JsPath \ "persons").write[Seq[Person]]

  implicit val homeWrites: Writes[Home] = homeWritesBuilder.apply(
    (home: Home) => (
      home._1.street,
      home._1.city,
      home._1.uuid,
      home._1.insertedAt,
      home._1.updatedAt,
      home._2
    )
  )

}