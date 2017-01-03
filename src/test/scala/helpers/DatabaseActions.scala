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

  def createDatabaseAction(dbName: String)
  (implicit ec: ExecutionContext): DBIO[Int] = {

    sqlu"""
      CREATE DATABASE "#$dbName"
    """

  }

  def dropDatabaseAction(dbName: String)
  (implicit ec: ExecutionContext): DBIO[Int] = {

    sqlu"""
      DROP DATABASE "#$dbName"
    """

  }

  def createInsertAddressAction(address: Address)
  (implicit ec: ExecutionContext): DBIO[String] = {

    sqlu"""
      INSERT INTO "ADDRESS" ("UUID", "STREET", "CITY", "INSERTED_AT", "UPDATED_AT")
        VALUES (${address.uuid.get}, ${address.street}, ${address.city}, ${address.insertedAt.get}, ${address.updatedAt.get})
    """
      .map(_ => address.uuid.get)

  }

  def createInsertPersonAction(person: Person)
  (implicit ec: ExecutionContext): DBIO[String] = {

    sqlu"""
      INSERT INTO "PERSON" ("UUID", "NAME", "AGE", "ADDRESS_UUID", "INSERTED_AT", "UPDATED_AT")
        VALUES (${person.uuid.get}, ${person.name}, ${person.age}, ${person.addressUuid}, ${person.insertedAt.get}, ${person.updatedAt.get})
    """
      .map(_ => person.uuid.get)

  }

  def createInsertTestDataAction
  (implicit ec: ExecutionContext): DBIO[Seq[Unit]] = {

    DBIO.sequence(
      Seq(
        // SET 001
        for {
          addressUuid <-
            createInsertAddressAction(
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
              createInsertPersonAction(
                Person(
                  name = "John Smith",
                  age = 25,
                  addressUuid = addressUuid,
                  uuid = Some("3104e3b9-708d-403d-ba9d-d78cd866bef6"),
                  insertedAt = Some(Instant.parse("2016-12-30T14:58:20Z")),
                  updatedAt = Some(Instant.parse("2016-12-30T14:58:20Z"))
                )
              ),
              createInsertPersonAction(
                Person(
                  name = "Jane Smith",
                  age = 23,
                  addressUuid = addressUuid,
                  uuid = Some("7b92b4cb-256c-4ef3-8651-b4bd20692e66"),
                  insertedAt = Some(Instant.parse("2016-12-30T14:58:24Z")),
                  updatedAt = Some(Instant.parse("2016-12-30T14:58:24Z"))
                )
              )
            )
          }
        } yield (),
        // SET 002
        for {
          addressUuid <-
            createInsertAddressAction(
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
              createInsertPersonAction(
                Person(
                  name = "Matti Meikäläinen",
                  age = 28,
                  addressUuid = addressUuid,
                  uuid = Some("b3f422f3-8aed-43a2-a52a-35fc0d41deae"),
                  insertedAt = Some(Instant.parse("2016-12-30T15:03:49Z")),
                  updatedAt = Some(Instant.parse("2016-12-30T15:03:49Z"))
                )
              ),
              createInsertPersonAction(
                Person(
                  name = "Maija Meikäläinen",
                  age = 27,
                  addressUuid = addressUuid,
                  uuid = Some("e4f716fc-8eda-428f-a3da-a25d9ffbe810"),
                  insertedAt = Some(Instant.parse("2016-12-30T15:03:51Z")),
                  updatedAt = Some(Instant.parse("2016-12-30T15:03:51Z"))
                )
              )
            )
          }
        } yield ()
      )
    )

  }

}
