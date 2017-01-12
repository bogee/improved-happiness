/*
 */

package dal

import java.time.Instant
import java.util.UUID

import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global

class PersonDataAccess extends MappedColumnTypes {

  implicit class PersonTableExtensions(t: PersonTable) {

    /**
      *
      * @param sort
      * @return
      */
    def mapOrderedColumn(sort: (String, String)) = {

      sort match {
        case (c, d) if c.equalsIgnoreCase("AGE") && d.equalsIgnoreCase("ASC") =>
          t.age.asc
        case (c, d) if c.equalsIgnoreCase("AGE") && d.equalsIgnoreCase("DESC") =>
          t.age.desc
        case (c, d) if c.equalsIgnoreCase("INSERTED_AT") && d.equalsIgnoreCase("ASC") =>
          t.insertedAt.asc
        case (c, d) if c.equalsIgnoreCase("INSERTED_AT") && d.equalsIgnoreCase("DESC") =>
          t.insertedAt.desc
        case (c, d) if c.equalsIgnoreCase("NAME") && d.equalsIgnoreCase("ASC") =>
          t.name.asc
        case (c, d) if c.equalsIgnoreCase("NAME") && d.equalsIgnoreCase("DESC") =>
          t.name.desc
        case (c, d) if c.equalsIgnoreCase("UPDATED_AT") && d.equalsIgnoreCase("ASC") =>
          t.updatedAt.asc
        case (c, d) if c.equalsIgnoreCase("UPDATED_AT") && d.equalsIgnoreCase("DESC") =>
          t.updatedAt.desc
      }

    }

  }

  implicit class PersonQueryExtensions[C[_]](q: Query[PersonTable, Person, C]) {

    /**
      *
      * @return
      */
    def withAddress = {

      q.join(TableQuery[AddressTable]).on(_.addressUuid === _.uuid)

    }

    /**
      *
      * @param pageNumber
      * @param pageSize
      * @return
      */
    def page(pageNumber: Long, pageSize: Long) = {

      q.drop((pageNumber - 1) * pageSize).take(pageSize)

    }

  }

  /**
    *
    * @param sort
    * @param pageNumber
    * @param pageSize
    * @return
    */
  def page(sort: (String, String), pageNumber: Long, pageSize: Long): DBIO[Seq[(Person, Address)]] = {

    // TODO: add query filters
    // TODO: use compiled query
    PersonQueries
      .sortBy {
        case (person: PersonTable) =>
          person.mapOrderedColumn(sort)
      }
      .page(pageNumber, pageSize)
      .withAddress
      .sortBy {
        case (person: PersonTable, _: AddressTable) =>
          person.mapOrderedColumn(sort)
      }
      .result
  }

  /**
    *
    * @param uuid
    * @return
    */
  def find(uuid: String): DBIO[Seq[Person]] = {
    PersonQueries.filterByUuid(uuid)
      .result
  }
  /**
    *
    * @param person
    * @return
    */
  def insert(person: Person): DBIO[Person] = {
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
  def updateAge(uuid: String, age: Int): DBIO[Option[Person]] = {
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
    val filterByUuid = Compiled((uuid: Rep[String]) =>
      this
        .filter(_.uuid === uuid)
    )

    /**
      *
      */
    val filterByAge= Compiled((age: Rep[Int]) =>
      this
        .filter(_.age === age)
    )

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