/*
 */

package dal

import java.time.Instant

import slick.driver.PostgresDriver.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape, Tag}

class PersonTable(tag: Tag)
  extends Table[Person](tag, "PERSON") with MappedColumnTypes {

  /**
    *
    * @return
    */
  def uuid: Rep[String] =
    column[String]("UUID", O.PrimaryKey)

  /**
    *
    * @return
    */
  def name: Rep[String] =
    column[String]("NAME")

  /**
    *
    * @return
    */
  def age: Rep[Int] =
    column[Int]("AGE")

  /**
    *
    * @return
    */
  def addressUuid: Rep[String] =
    column[String]("ADDRESS_UUID")

  /**
    *
    * @return
    */
  def insertedAt: Rep[Instant] =
    column[Instant]("INSERTED_AT")

  /**
    *
    * @return
    */
  def updatedAt: Rep[Instant] =
    column[Instant]("UPDATED_AT")

  /**
    *
    * @return
    */
  def * : ProvenShape[Person] = (
    name,
    age,
    addressUuid,
    uuid.?,
    insertedAt.?,
    updatedAt.?
  ) <> (Person.tupled, Person.unapply)

  /**
    *
    * @return
    */
  def address: ForeignKeyQuery[AddressTable, Address] =
    foreignKey("ADDRESS", addressUuid, TableQuery[AddressTable])(_.uuid)

}
