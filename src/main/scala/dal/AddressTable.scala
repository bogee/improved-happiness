/*
 */

package dal

import java.time.Instant

import slick.driver.PostgresDriver.api._
import slick.lifted.{ProvenShape, Tag}

class AddressTable(tag: Tag)
  extends Table[Address](tag, "ADDRESS") with MappedColumnTypes {

  /**
    *
    * @return
    */
  def street: Rep[String] =
    column[String]("STREET")

  /**
    *
    * @return
    */
  def city: Rep[String] =
    column[String]("CITY")

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
  def * : ProvenShape[Address] = (
    street,
    city,
    uuid.?,
    insertedAt.?,
    updatedAt.?
  ) <> (Address.tupled, Address.unapply)

}
