package domain

import dal.{Address, Person}

package object club {

  type Member = (Person, Address)

  type Home = (Address, Seq[Person])

}
