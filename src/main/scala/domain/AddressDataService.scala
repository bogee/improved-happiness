/*
 */

package domain

import javax.inject._

import dal.{Address, AddressDataAccess, Person}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.driver.JdbcProfile

import scala.concurrent.Future


class AddressDataService @Inject()(@NamedDatabase("foo") val dbConfigProvider: DatabaseConfigProvider, val dao: AddressDataAccess)
  extends IDataService with HasDatabaseConfigProvider[JdbcProfile] {

  /**
    *
    * @param uuid
    * @return
    */
  def findAddress(uuid: String): Future[Option[Address]] = {
    db.run(dao.findAddress(uuid))
  }

  /**
    *
    * @param uuid
    * @return
    */
  def findAddressAndPerson(uuid: String): Future[Seq[(Address, Person)]] = {
    db.run(dao.findAddressAndPerson(uuid))
  }

  /**
    *
    * @param address
    * @return
    */
  def insertAddress(address: Address): Future[Address] = {
    db.run(dao.insertAddress(address))
  }

  /** Free all resources allocated by Slick
    * @return
    */
  def shutdown() = {
    db.shutdown
  }
}
