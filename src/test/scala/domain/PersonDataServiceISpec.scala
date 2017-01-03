/*
 */

package domain

import dal.Person
import org.specs2.mutable.Specification
import org.specs2.specification.core.Env
import scopes.WithDataService

import scala.concurrent.duration._

class PersonDataServiceISpec(env : Env) extends Specification {

  implicit val ee = env.executionEnv
  implicit val ec = env.executionContext

  "PersonService" should {

    "find person with its uuid" in new WithDataService[PersonDataService] {

      // Act
      val actFuture = dataService.findPerson("3104e3b9-708d-403d-ba9d-d78cd866bef6")

      // Assert
      actFuture must {
        beSome[Person].awaitFor(10.seconds)
      }
    }

  }
}
