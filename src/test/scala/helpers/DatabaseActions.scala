/*
 */

package helpers

import java.time.Instant

import dal.{Address, Person}
import slick.driver.PostgresDriver.api._
import slick.jdbc.{PositionedParameters, SetParameter}

import scala.concurrent.ExecutionContext

trait DatabaseActions {

  implicit object SetInstantParameter extends SetParameter[Instant] {
    def apply(v: Instant, pp: PositionedParameters) {
      pp.setTimestamp(java.sql.Timestamp.from(v))
    }
  }

  /**
    *
    * @param dbName
    * @param ec
    * @return
    */
  def createDatabase(dbName: String)
  (implicit ec: ExecutionContext): DBIO[Int] = {

    sqlu"""
      CREATE DATABASE "#$dbName"
    """

  }

  /**
    *
    * @param dbName
    * @param ec
    * @return
    */
  def dropDatabase(dbName: String)
  (implicit ec: ExecutionContext): DBIO[Int] = {

    sqlu"""
      DROP DATABASE "#$dbName"
    """

  }

  /**
    *
    * @param address
    * @param ec
    * @return
    */
  def insertAddress(address: Address)
  (implicit ec: ExecutionContext): DBIO[String] = {

    sqlu"""
      INSERT INTO "ADDRESS" ("UUID", "STREET", "CITY", "INSERTED_AT", "UPDATED_AT")
        VALUES (${address.uuid.get}, ${address.street}, ${address.city}, ${address.insertedAt.get}, ${address.updatedAt.get})
    """
      .map(_ => address.uuid.get)

  }

  /**
    *
    * @param person
    * @param ec
    * @return
    */
  def insertPerson(person: Person)
  (implicit ec: ExecutionContext): DBIO[String] = {

    sqlu"""
      INSERT INTO "PERSON" ("UUID", "NAME", "AGE", "INSERTED_AT", "UPDATED_AT", "ADDRESS_UUID")
        VALUES (${person.uuid.get}, ${person.name}, ${person.age}, ${person.insertedAt.get}, ${person.updatedAt.get}, ${person.addressUuid.get})
    """
      .map(_ => person.uuid.get)

  }

  /**
    *
    * @param dbName
    * @param ec
    * @return
    */
  def lockDatabase(dbName: String)
  (implicit ec: ExecutionContext): DBIO[Int] = {
    sqlu"""
      UPDATE pg_database
      SET datallowconn = false
      WHERE datname = '#$dbName'
    """
  }

  /**
    *
    * @param dbName
    * @param ec
    * @return
    */
  def terminateBackendsForDatabase(dbName: String)
  (implicit ec: ExecutionContext): DBIO[Seq[Boolean]] = {

    sql"""
      SELECT pg_terminate_backend(pg_stat_activity.pid)
      FROM pg_stat_activity
      WHERE pg_stat_activity.datname = '#$dbName'
        AND pid <> pg_backend_pid()
    """
      .as[Boolean]

  }

  /**
    *
    * @param ec
    * @return
    */
  def insertTestData
  (implicit ec: ExecutionContext): DBIO[Unit] = {

    DBIO.seq(
      // SET 001
      for {
        addressUuid <-
          insertAddress(
            Address(
              street = " ",
              city = "Seattle",
              uuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4"),
              insertedAt = Some(Instant.parse("2016-12-30T14:56:44Z")),
              updatedAt = Some(Instant.parse("2016-12-30T14:56:44Z"))
            )
          )
        _ <- {
          DBIO.seq(
            insertPerson(
              Person(
                name = "John Smith",
                age = 25,
                uuid = Some("3104e3b9-708d-403d-ba9d-d78cd866bef6"),
                insertedAt = Some(Instant.parse("2016-12-30T14:58:20Z")),
                updatedAt = Some(Instant.parse("2016-12-30T14:58:20Z")),
                addressUuid = Some(addressUuid)
              )
            ),
            insertPerson(
              Person(
                name = "Jane Smith",
                age = 23,
                uuid = Some("7b92b4cb-256c-4ef3-8651-b4bd20692e66"),
                insertedAt = Some(Instant.parse("2016-12-30T14:58:24Z")),
                updatedAt = Some(Instant.parse("2016-12-30T14:58:24Z")),
                addressUuid = Some(addressUuid)
              )
            )
          )
        }
      } yield (),
      // SET 002
      for {
        addressUuid <-
          insertAddress(
            Address(
              street = " ",
              city = "Helsinki",
              uuid = Some("0c4da69a-4da3-4481-8356-61398e5c729a"),
              insertedAt = Some(Instant.parse("2016-12-30T15:01:23Z")),
              updatedAt = Some(Instant.parse("2016-12-30T15:01:23Z"))
            )
          )
        _ <- {
          DBIO.seq(
            insertPerson(
              Person(
                name = "Matti Meikäläinen",
                age = 28,
                uuid = Some("b3f422f3-8aed-43a2-a52a-35fc0d41deae"),
                insertedAt = Some(Instant.parse("2016-12-30T15:03:49Z")),
                updatedAt = Some(Instant.parse("2016-12-30T15:03:49Z")),
                addressUuid = Some(addressUuid)
              )
            ),
            insertPerson(
              Person(
                name = "Maija Meikäläinen",
                age = 27,
                uuid = Some("e4f716fc-8eda-428f-a3da-a25d9ffbe810"),
                insertedAt = Some(Instant.parse("2016-12-30T15:03:51Z")),
                updatedAt = Some(Instant.parse("2016-12-30T15:03:51Z")),
                addressUuid = Some(addressUuid)
              )
            )
          )
        }
      } yield (),
      // SET 003
      for {
        addressUuid <-
        insertAddress(
          Address(
            street = " ",
            city = "Budapest",
            uuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3"),
            insertedAt = Some(Instant.parse("2016-12-30T15:06:37Z")),
            updatedAt = Some(Instant.parse("2016-12-30T15:06:37Z"))
          )
        )
        _ <- {
          DBIO.seq(
            insertPerson(
              Person(
                name = "János Minta",
                age = 35,
                uuid = Some("47321ad0-f1a9-4313-a09e-cead428fd8ba"),
                insertedAt = Some(Instant.parse("2016-12-30T15:06:45Z")),
                updatedAt = Some(Instant.parse("2016-12-30T15:06:45Z")),
                addressUuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3")
              )
            ),
            insertPerson(
              Person(
                name = "Kata Minta",
                age = 38,
                uuid = Some("60b433e8-b3a4-44dc-97e1-13f8210465b3"),
                insertedAt = Some(Instant.parse("2016-12-30T15:06:47Z")),
                updatedAt = Some(Instant.parse("2016-12-30T15:06:47Z")),
                addressUuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3")
              )
            )
          )
        }
      } yield ()
    )

  }

}
