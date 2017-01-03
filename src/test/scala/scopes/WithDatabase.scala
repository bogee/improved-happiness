/*
 */

package scopes

import helpers.{DatabaseHelper, Environment}
import org.specs2.mutable.BeforeAfter
import slick.driver.PostgresDriver.api.Database

import scala.util.Try

abstract class WithDatabase extends BeforeAfter with DatabaseHelper with Environment {

  lazy val database = Database.forURL(
    url = s"jdbc:postgresql://$dbHost:$dbPort/$dbName",
    user = dbUser,
    password = dbPassword,
    driver = dbDriver
  )

  protected def prepareDropDatabase() = {

    val closeTry = Try {
      database.close()
    }

    closeTry.failed
      .foreach(err => throw new Exception(s"failed to close $database", err))

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
