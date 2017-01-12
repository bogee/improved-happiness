/*
 */

package dal

import java.time.Instant

import org.specs2.mutable._
import org.specs2.specification.core.Env
import scopes.WithDatabase
import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery

import scala.concurrent.duration._

class PersonDataAccessISpec(env : Env) extends Specification {

  implicit val ee = env.executionEnv
  implicit val ec = env.executionContext

  "PersonDataAccess" should {

    "find person with its uuid" in new WithDatabase {

      // Arrange
      val dao = new PersonDataAccess

      // Act
      val actFuture = database.run {
        dao.find("3104e3b9-708d-403d-ba9d-d78cd866bef6")
          .map(_.headOption)
      }

      // Assert
      actFuture must {
        beSome[Person].awaitFor(10.seconds)
      }
    }

    "insert person" in new WithDatabase {

      // Arrange
      val dao = new PersonDataAccess

      val person = Person(
        name = "Baby Smith",
        age = 0,
        uuid = Some("6d6de9fe-13ed-4d91-937a-314ef4630d04"),
        addressUuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4")
      )

      // Act
      val actFuture = database.run {
        dao.insert(person)
      }

      // Assert
      actFuture
        .flatMap { _ =>
          database.run {
            TableQuery[PersonTable]
              .filter(_.uuid === "6d6de9fe-13ed-4d91-937a-314ef4630d04")
              .result
              .headOption
          }
        }
        .must {
          beSome[Person]
            .which { person =>
              person.name mustEqual "Baby Smith"
              person.age mustEqual 0
              person.insertedAt must beSome[Instant]
              person.updatedAt must beSome[Instant]
              person.addressUuid must beSome[String]("70ee1994-41a1-4b7b-85b2-924ffc836cb4")
            }
            .awaitFor(10.seconds)
        }
    }

    "update person age" in new WithDatabase {

      // Arrange
      val dao = new PersonDataAccess

      // Act
      val actFuture =  database.run {
        dao.updateAge("7b92b4cb-256c-4ef3-8651-b4bd20692e66", 24)
      }

      // Assert
      actFuture
        .flatMap { _ =>
          database.run {
            TableQuery[PersonTable]
              .filter(_.uuid === "7b92b4cb-256c-4ef3-8651-b4bd20692e66")
              .result
              .headOption
          }
        }
        .must {
          beSome[Person]
            .which { row =>
              row.age mustEqual 24
              row.updatedAt must beSome[Instant].which(_ must beGreaterThan(Instant.parse("2016-12-30T14:58:24Z")))
            }
            .awaitFor(10.seconds)
        }
    }

  }

}
