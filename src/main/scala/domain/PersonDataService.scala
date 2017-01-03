/*
 */

package domain

import javax.inject.Inject

import dal.{Address, Person, PersonDataAccess}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.driver.JdbcProfile

import scala.concurrent.Future

class PersonDataService @Inject()(@NamedDatabase("foo") val dbConfigProvider: DatabaseConfigProvider, val dao: PersonDataAccess)
  extends IDataService with HasDatabaseConfigProvider[JdbcProfile] {

  /**
    *
    * @param uuid
    * @return
    */
  def findPerson(uuid: String): Future[Option[Person]] = {
    db.run(dao.findPerson(uuid))
  }

  /**
    *
    * @param uuid
    * @return
    */
  def findPersonAndAddress(uuid: String): Future[Option[(Person, Address)]] = {
    db.run(dao.findPersonAndAddress(uuid))
  }

  /**
    *
    * @param person
    * @return
    */
  def insertPerson(person: Person): Future[Person] = {
    db.run(dao.insertPerson(person))
  }

  /**
    *
    * @param uuid
    * @param age
    * @return
    */
  def updatePersonAge(uuid: String, age: Int): Future[Option[Person]] = {
    db.run(dao.updatePersonAge(uuid, age))
  }

  /** Free all resources allocated by Slick
    * @return
    */
  def shutdown() = {
    db.shutdown
  }

}
