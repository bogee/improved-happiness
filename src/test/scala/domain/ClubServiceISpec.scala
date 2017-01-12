/*
 */

package domain

import java.time.Instant

import dal.{Address, Person}
import org.specs2.mutable.Specification
import org.specs2.specification.core.Env
import scopes.WithDataService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class ClubServiceISpec(env : Env) extends Specification {

  implicit val ee = env.executionEnv
  implicit val ec = env.executionContext

  "ClubService" should {

    "register a new member" in new WithDataService[ClubService]("club") {

      // Arrange

      val address = Address(street = "Friedrichstraße", city = "Berlin")
      val person = Person(name = "Max Mustermann", age = 32)

      // Act

      val actFuture = dataService.registerNewMember(person, address)

      // Assert

      actFuture.map(_._1.name) must {
        beEqualTo("Max Mustermann").awaitFor(10.seconds)
      }

      actFuture.map(_._1.age) must {
        beEqualTo(32).awaitFor(10.seconds)
      }

      actFuture.map(_._1.uuid) must {
        beSome[String].awaitFor(10.seconds)
      }

      actFuture.map(_._1.insertedAt) must {
        beSome[Instant].awaitFor(10.seconds)
      }

      actFuture.map(_._1.updatedAt) must {
        beSome[Instant].awaitFor(10.seconds)
      }

      actFuture.map(member => member._1.addressUuid == member._2.uuid) must {
        beEqualTo(true).awaitFor(10.seconds)
      }

      actFuture.map(_._2.street) must {
        beEqualTo("Friedrichstraße").awaitFor(10.seconds)
      }

      actFuture.map(_._2.city) must {
        beEqualTo("Berlin").awaitFor(10.seconds)
      }

      actFuture.map(_._2.uuid) must {
        beSome[String].awaitFor(10.seconds)
      }

      actFuture.map(_._2.insertedAt) must {
        beSome[Instant].awaitFor(10.seconds)
      }

      actFuture.map(_._2.updatedAt) must {
        beSome[Instant].awaitFor(10.seconds)
      }

    }

    "register a new home" in new WithDataService[ClubService]("club") {

      // Arrange

      val address = Address(street = "Friedrichstraße", city = "Berlin")
      val persons = Seq(
        Person(name = "Max Mustermann", age = 32),
        Person(name = "Erika Mustermann", age = 31)
      )

      // Act

      val actFuture = dataService.registerNewHome(address, persons)

      // Assert

      actFuture.map(_._1.street) must {
        beEqualTo("Friedrichstraße").awaitFor(10.seconds)
      }

      actFuture.map(_._1.city) must {
        beEqualTo("Berlin").awaitFor(10.seconds)
      }

      actFuture.map(_._1.uuid) must {
        beSome[String].awaitFor(10.seconds)
      }

      actFuture.map(_._1.insertedAt) must {
        beSome[Instant].awaitFor(10.seconds)
      }

      actFuture.map(_._1.updatedAt) must {
        beSome[Instant].awaitFor(10.seconds)
      }

      actFuture.map(_._2(0).name) must {
        beEqualTo("Max Mustermann").awaitFor(10.seconds)
      }

      actFuture.map(_._2(0).age) must {
        beEqualTo(32).awaitFor(10.seconds)
      }

      actFuture.map(_._2(0).uuid) must {
        beSome[String].awaitFor(10.seconds)
      }

      actFuture.map(_._2(0).insertedAt) must {
        beSome[Instant].awaitFor(10.seconds)
      }

      actFuture.map(_._2(0).updatedAt) must {
        beSome[Instant].awaitFor(10.seconds)
      }

      actFuture.map(home => home._2(0).addressUuid == home._1.uuid) must {
        beEqualTo(true).awaitFor(10.seconds)
      }

    }

    "list first page containing two members sorted by age" in new WithDataService[ClubService]("club") {

      // Act

      val actFuture = dataService.listMembers(("age", "desc"), 1, 4)

      // Assert

      actFuture.map(members => members.length) must {
        beEqualTo(4).awaitFor(10.seconds)
      }

      actFuture.map(members => members(0)._1.name) must {
        beEqualTo("Kata Minta").awaitFor(10.seconds)
      }

      actFuture.map(members => members(1)._1.name) must {
        beEqualTo("János Minta").awaitFor(10.seconds)
      }

      actFuture.map(members => members(2)._1.name) must {
        beEqualTo("Matti Meikäläinen").awaitFor(10.seconds)
      }

      actFuture.map(members => members(3)._1.name) must {
        beEqualTo("Maija Meikäläinen").awaitFor(10.seconds)
      }

    }

    "list first page containing two homes sorted by city" in new WithDataService[ClubService]("club") {

      // Act

      val actFuture = dataService.listHomes(("city", "asc"), 1, 2)

      // Assert

      actFuture.map(members => members.length) must {
        beEqualTo(2).awaitFor(10.seconds)
      }

      actFuture.map(members => members(0)._1.city) must {
        beEqualTo("Budapest").awaitFor(10.seconds)
      }

      actFuture.map(members => members(1)._1.city) must {
        beEqualTo("Helsinki").awaitFor(10.seconds)
      }

    }

  }
}
