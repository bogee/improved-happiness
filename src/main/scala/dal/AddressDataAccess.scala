/*
 */

package dal

import java.time.Instant
import java.util.UUID

import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery

class AddressDataAccess extends MappedColumnTypes {

  implicit class AddressQueryExtensions[C[_]](q: Query[AddressTable, Address, C]) {
    def withPersons = q.join(TableQuery[PersonTable]).on(_.uuid === _.addressUuid)
  }

  /**
    *
    * @param uuid
    * @return
    */
  def findAddress(uuid: String): DBIO[Option[Address]] = {
    AddressQueries.filterByUuid(uuid)
      .result
      .headOption
  }

  /**
    *
    * @param uuid
    * @return
    */
  def findAddressAndPerson(uuid: String): DBIO[Seq[(Address, Person)]] = {
    AddressQueries.filterByUuid(uuid)
      .map(query => query.withPersons)
      .result
  }

  /**
    *
    * @param address
    * @return
    */
  def insertAddress(address: Address): DBIO[Address] = {
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
    val filterByUuid = Parameters[String]
      .flatMap {
        case (uuid) =>
          this.filter(_.uuid === uuid)
      }

    /**
      *
      */
    val filterByCity = Parameters[String]
      .flatMap {
        case (city) =>
          this.filter(_.city === city)
      }

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

