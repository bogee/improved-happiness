/*
 */

package domain

import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile

trait IDataService extends HasDatabaseConfigProvider[JdbcProfile]{

}
