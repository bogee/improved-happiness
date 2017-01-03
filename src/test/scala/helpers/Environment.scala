/*
 */

package helpers

import scala.util.Random

trait Environment {

  lazy val dbHost = "localhost"

  lazy val dbPort = "32768"

  lazy val dbName = new Random().alphanumeric.take(15).mkString

  lazy val dbUser = "postgres"

  lazy val dbPassword = "password"

  lazy val dbDriver = "org.postgresql.Driver"

}
