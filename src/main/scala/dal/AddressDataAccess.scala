/*
 */

package dal

import java.time.Instant
import java.util.UUID

import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery

class AddressDataAccess extends MappedColumnTypes {

  implicit class AddressTableExtensions(t: AddressTable) {

    /**
      *
      * @param sort
      * @return
      */
    def mapOrderedColumn(sort: (String, String)) = {

      sort match {
        case (c, d) if c.equalsIgnoreCase("CITY") && d.equalsIgnoreCase("ASC") =>
          t.city.asc
        case (c, d) if c.equalsIgnoreCase("CITY") && d.equalsIgnoreCase("DESC") =>
          t.city.desc
        case (c, d) if c.equalsIgnoreCase("INSERTED_AT") && d.equalsIgnoreCase("ASC") =>
          t.insertedAt.asc
        case (c, d) if c.equalsIgnoreCase("INSERTED_AT") && d.equalsIgnoreCase("DESC") =>
          t.insertedAt.desc
        case (c, d) if c.equalsIgnoreCase("STREET") && d.equalsIgnoreCase("ASC") =>
          t.street.asc
        case (c, d) if c.equalsIgnoreCase("STREET") && d.equalsIgnoreCase("DESC") =>
          t.street.desc
        case (c, d) if c.equalsIgnoreCase("UPDATED_AT") && d.equalsIgnoreCase("ASC") =>
          t.updatedAt.asc
        case (c, d) if c.equalsIgnoreCase("UPDATED_AT") && d.equalsIgnoreCase("DESC") =>
          t.updatedAt.desc
      }

    }

  }

  implicit class AddressQueryExtensions[C[_]](q: Query[AddressTable, Address, C]) {

    /**
      *
      * @return
      */
    def withPerson = {

      q.join(TableQuery[PersonTable]).on(_.uuid === _.addressUuid)

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
  def page(sort: (String, String), pageNumber: Long, pageSize: Long): DBIO[Seq[(Address, Person)]] = {

    // TODO: add query filters
    // TODO: use compiled query
    AddressQueries
      .sortBy {
        case (address: AddressTable) =>
          address.mapOrderedColumn(sort)
      }
      .page(pageNumber, pageSize)
      .withPerson
      .sortBy {
        case (address: AddressTable, _: PersonTable) =>
          address.mapOrderedColumn(sort)
      }
      .result
  }

  /**
    *
    * @param uuid
    * @return
    */
  def find(uuid: String): DBIO[Seq[Address]] = {
    AddressQueries.filterByUuid(uuid)
      .result
  }

  /**
    *
    * @param address
    * @return
    */
  def insert(address: Address): DBIO[Address] = {
    AddressQueries.insertAddress += (
      address.uuid match {
        case Some(_) =>
          address.copy(
            insertedAt = Some(Instant.now),
            updatedAt = Some(Instant.now())
          )
        case None =>
          address.copy(
            uuid = Some(UUID.randomUUID().toString),
            insertedAt = Some(Instant.now),
            updatedAt = Some(Instant.now())
          )
      }
    )
  }

  object AddressQueries extends TableQuery(new AddressTable(_)) {

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
    val filterByCity = Compiled((city: Rep[String]) =>
      this
        .filter(_.city === city)
    )

    /**
      *
      */
    val insertAddress = this
      .returning {
        this.map(_.uuid)
      }
      .into {
        (address, uuid) =>
          address.copy(uuid = Some(uuid))
      }

  }

}

