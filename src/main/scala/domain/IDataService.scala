/*
 */

package domain

import scala.concurrent.Future

trait IDataService {

  def shutdown(): Future[Unit]

}
