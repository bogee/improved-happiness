/*
 */

package helpers

import org.flywaydb.core.Flyway
import org.postgresql.util.PSQLException
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

trait DatabaseHelper extends DatabaseActions {

  val dbHost: String

  val dbPort: String

  val dbName: String

  val dbUser: String

  val dbPassword: String

  val dbDriver: String

  protected def using[T <: { def close() }]
  (resource: T)
  (block: T => Unit)
  {
    try {
      block(resource)
    } finally {
      if (resource != null) resource.close()
    }
  }

  protected def createDatabase(): Unit = {

    val dbUrl = s"jdbc:postgresql://$dbHost:$dbPort/"

    try {
      using(Database.forURL(url = dbUrl, user = dbUser, password = dbPassword, driver = dbDriver)) {
        db =>
          Await.result(db.run(createDatabaseAction(dbName)), 10.seconds)
      }
    } catch {
      case e:PSQLException =>
        throw e
      case e:Throwable => throw e
    }

  }

  protected def dropDatabase(): Unit = {

    val dbUrl = s"jdbc:postgresql://$dbHost:$dbPort/"

    try {
      using(Database.forURL(url = dbUrl, user = dbUser, password = dbPassword, driver = dbDriver)) {
        db =>
          Await.result(db.run(dropDatabaseAction(dbName)), 10.seconds)
      }
    } catch {
      case e:PSQLException =>
        throw e
      case e:Throwable => throw e
    }

  }

  protected def migrateDatabase(): Unit = {

    val dbUrl = s"jdbc:postgresql://$dbHost:$dbPort/$dbName"

    try {
      val flyway: Flyway = new Flyway()
      flyway.setDataSource(dbUrl, dbUser, dbPassword)
      flyway.migrate()
    } catch {
      case e:PSQLException =>
        throw e
      case e:Throwable => throw e
    }

  }

  protected def populateDatabase(): Unit = {

    val dbUrl = s"jdbc:postgresql://$dbHost:$dbPort/$dbName"

    try {
      using(Database.forURL(url = dbUrl, user = dbUser, password = dbPassword, driver = dbDriver)) {
        db =>
          Await.result(db.run(createInsertTestDataAction), 10.seconds)
      }
    } catch {
      case e:PSQLException =>
        throw e
      case e:Throwable => throw e
    }

  }

}
