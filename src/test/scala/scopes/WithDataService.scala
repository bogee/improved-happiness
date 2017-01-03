/*
 */

package scopes

import domain.IDataService
import helpers.{DatabaseHelper, Environment}
import org.specs2.mutable.BeforeAfter
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.reflect.ClassTag

abstract class WithDataService[T <: IDataService]()(implicit c: ClassTag[T]) extends BeforeAfter
  with DatabaseHelper with Environment {

  private lazy val injector: Injector = GuiceApplicationBuilder()
    .configure(
      Map(
        "slick.dbs.foo.driver" -> "slick.driver.PostgresDriver$",
        "slick.dbs.foo.db.driver" -> dbDriver,
        "slick.dbs.foo.db.url" -> s"jdbc:postgresql://$dbHost:$dbPort/$dbName",
        "slick.dbs.foo.db.user" -> dbUser,
        "slick.dbs.foo.db.password" -> dbPassword
      )
    ).injector()

  lazy val dataService: T = injector.instanceOf[T]

  def prepareDropDatabase(): Unit = {

    Await.result(dataService.shutdown(), 10.seconds)

  }

  def after: Any = {

    prepareDropDatabase()

    dropDatabase()

  }

  def before: Any = {

    createDatabase()

    migrateDatabase()

    populateDatabase()

  }

}
