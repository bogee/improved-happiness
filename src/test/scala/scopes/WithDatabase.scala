/*
 */

package scopes

import helpers.{DatabaseHelper, Environment}
import org.specs2.mutable.BeforeAfter
import slick.driver.PostgresDriver.api.Database

abstract class WithDatabase extends BeforeAfter with DatabaseHelper with Environment {

  lazy val database = Database.forURL(
    url = s"jdbc:postgresql://$dbHost:$dbPort/$dbName",
    user = dbUser,
    password = dbPassword,
    driver = dbDriver
  )

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
