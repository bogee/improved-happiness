/*
 */

package domain

import javax.inject.Inject

import dal._
import domain.club.{Home, Member}
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ClubService @Inject()(
  @NamedDatabase("club") val dbConfigProvider: DatabaseConfigProvider,
  val personDAO: PersonDataAccess,
  val addressDAO: AddressDataAccess
)
extends IDataService {

  /**
    *
    * @param person
    * @param address
    * @return
    */
  def registerNewMember(person: Person, address: Address): Future[Member] = {

    val action: DBIO[Member] =
      for {
        address <- addressDAO.insert(address)
        person <- personDAO.insert(person.copy(addressUuid = address.uuid))
      } yield (person, address)

    db.run(action.transactionally)

  }

  /**
    *
    * @param address
    * @param persons
    */
  def registerNewHome(address: Address, persons: Seq[Person]): Future[Home] = {

    val action: DBIO[Home] =
      for {
        address <- addressDAO.insert(address)
        persons <- DBIO.sequence(persons.map(p => personDAO.insert(p.copy(addressUuid = address.uuid))))
      } yield (address, persons)

    db.run(action.transactionally)

  }



  /**
    *
    * @param pageNumber
    * @param pageSize
    * @return
    */
  def listMembers(sort: (String, String), pageNumber: Int, pageSize: Int) : Future[Seq[Member]] = {

    val action: DBIO[Seq[Member]] =
      personDAO.page(sort, pageNumber, pageSize)

    db.run(action)

  }

  /**
    *
    * @param pageNumber
    * @param pageSize
    * @return
    */
  def listHomes(sort: (String, String), pageNumber: Int, pageSize: Int): Future[Seq[Home]] = {

    val action: DBIO[Seq[Home]] =
      addressDAO.page(sort, pageNumber, pageSize)
        .map(ts =>
          ts.map(_._1).distinct.map(a =>
            (a, ts.map(_._2).filter(p => p.addressUuid == a.uuid))
          )
        )

    db.run(action)

  }

}
