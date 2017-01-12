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

class AddressDataAccessISpec(env : Env) extends Specification {

  implicit val ee = env.executionEnv
  implicit val ec = env.executionContext

  "AddressDataAccess" should {

    "find address with its uuid" in new WithDatabase {

      // Arrange
      val dao = new AddressDataAccess

      // Act
      val actFuture =  database.run {
        dao.find("70ee1994-41a1-4b7b-85b2-924ffc836cb4")
          .map(_.headOption)
      }

      // Assert
      actFuture must {
        beSome[Address].awaitFor(10.seconds)
      }
    }

    "insert address" in new WithDatabase {

      // Arrange
      val dao = new AddressDataAccess

      val address = Address(
        street = "Via del Corso",
        city = "Rome",
        uuid = Some("c0f494f6-14bf-47a8-9e04-12536fc1d979")
      )

      // Act
      val actFuture = database.run {
        dao.insert(address)
      }

      // Assert
      actFuture
        .flatMap { _ =>
          database.run {
            TableQuery[AddressTable]
              .filter(_.uuid === "c0f494f6-14bf-47a8-9e04-12536fc1d979")
              .result
              .headOption
          }
        }
        .must {
          beSome[Address]
            .which { address =>
              address.street mustEqual "Via del Corso"
              address.city mustEqual "Rome"
              address.insertedAt must beSome[Instant]
              address.updatedAt must beSome[Instant]
            }
            .awaitFor(10.seconds)
        }
    }

  }

}
