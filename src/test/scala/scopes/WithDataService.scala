/*
 */

package scopes

import domain.IDataService
import helpers.{DatabaseHelper, Environment}
import org.specs2.mutable.BeforeAfter
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder

import scala.reflect.ClassTag

abstract class WithDataService[T <: IDataService](databaseName: String)(implicit c: ClassTag[T]) extends BeforeAfter
  with DatabaseHelper with Environment {

  private lazy val injector: Injector = GuiceApplicationBuilder()
    .configure(
      Map(
        s"slick.dbs.$databaseName.driver" -> "slick.driver.PostgresDriver$",
        s"slick.dbs.$databaseName.db.driver" -> dbDriver,
        s"slick.dbs.$databaseName.db.url" -> s"jdbc:postgresql://$dbHost:$dbPort/$dbName",
        s"slick.dbs.$databaseName.db.user" -> dbUser,
        s"slick.dbs.$databaseName.db.password" -> dbPassword
      )
    ).injector()

  lazy val dataService: T = injector.instanceOf[T]

  def after: Any = {

    lockDatabase()

    terminateBackend()

    dropDatabase()

  }

  def before: Any = {

    createDatabase()

    migrateDatabase()

    populateDatabase()

  }

}
