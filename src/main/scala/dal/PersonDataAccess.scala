/*
 */

package dal

import java.time.Instant
import java.util.UUID

import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global

class PersonDataAccess extends MappedColumnTypes {

  implicit class PersonQueryExtensions[C[_]](q: Query[PersonTable, Person, C]) {
    def withAddress = q.join(TableQuery[AddressTable]).on(_.addressUuid === _.uuid)
  }

  /**
    *
    * @param uuid
    * @return
    */
  def findPerson(uuid: String): DBIO[Option[Person]] = {
    PersonQueries.filterByUuid(uuid)
      .result
      .headOption
  }

  /**
    *
    * @param uuid
    * @return
    */
  def findPersonAndAddress(uuid: String): DBIO[Option[(Person, Address)]] = {
    PersonQueries.filterByUuid(uuid)
      .map(query => query.withAddress)
      .result
      .headOption
  }

  /**
    *
    * @param person
    * @return
    */
  def insertPerson(person: Person): DBIO[Person] = {
    PersonQueries.insertPerson += (
      person.uuid match {
        case Some(_) =>
          val now = Instant.now
          person.copy(
            insertedAt = Some(now),
            updatedAt = Some(now)
          )
        case None =>
          val now = Instant.now
          person.copy(
            uuid = Some(UUID.randomUUID().toString),
            insertedAt = Some(now),
            updatedAt = Some(now)
          )
      }
    )
  }

  /**
    *
    * @param uuid
    * @param age
    * @return
    */
  def updatePersonAge(uuid: String, age: Int): DBIO[Option[Person]] = {
    for {
      Some(person: Person) <- PersonQueries.filterByUuid(uuid).result.headOption
      updatedPerson <- {
        PersonQueries.insertPerson.insertOrUpdate(
          person.copy(age = age, updatedAt = Some(Instant.now))
        )
      }
    } yield updatedPerson
  }

  object PersonQueries extends TableQuery(new PersonTable(_)) {

    /**
      *
      */
    val filterByUuid = Parameters[String]
      .flatMap {
        case (uuid) =>
          this.filter(_.uuid === uuid)
      }

    /**
      *
      */
    val filterByAge = Parameters[Int]
      .flatMap {
        case (age) =>
          this.filter(_.age === age)
      }

    /**
      *
      */
    val insertPerson = this
      .returning {
        this.map(_.uuid)
      }
      .into {
        (person, uuid) =>
          person.copy(uuid = Some(uuid))
      }

  }

}