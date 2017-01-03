/*
 */

package domain

import dal.Address
import org.specs2.mutable.Specification
import org.specs2.specification.core.Env
import scopes.WithDataService

import scala.concurrent.duration._

class AddressDataServiceISpec(env : Env) extends Specification {

  implicit val ee = env.executionEnv
  implicit val ec = env.executionContext

  "AddressService" should {

    "find address with its uuid" in new WithDataService[AddressDataService] {

      // Act
      val actFuture = dataService.findAddress("70ee1994-41a1-4b7b-85b2-924ffc836cb4")

      // Assert
      actFuture must {
        beSome[Address].awaitFor(10.seconds)
      }
    }

  }
}
