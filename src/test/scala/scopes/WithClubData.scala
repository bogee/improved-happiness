/*
 */

package scopes

import java.time.Instant

import dal.{Address, Person}
import domain.{ClubFormats, club}
import org.specs2.specification.Scope

abstract class WithClubData extends Scope with ClubFormats {

  val clubMembers: Seq[club.Member] = Seq(
    (
      Person(
        name = "John Smith",
        age = 25,
        uuid = Some("3104e3b9-708d-403d-ba9d-d78cd866bef6"),
        insertedAt = Some(Instant.parse("2016-12-30T14:58:20Z")),
        updatedAt = Some(Instant.parse("2016-12-30T14:58:20Z")),
        addressUuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4")
      ),
      Address(
        street = " ",
        city = "Seattle",
        uuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4"),
        insertedAt = Some(Instant.parse("2016-12-30T14:56:44Z")),
        updatedAt = Some(Instant.parse("2016-12-30T14:56:44Z"))
      )
      ),
    (
      Person(
        name = "Jane Smith",
        age = 23,
        uuid = Some("7b92b4cb-256c-4ef3-8651-b4bd20692e66"),
        insertedAt = Some(Instant.parse("2016-12-30T14:58:24Z")),
        updatedAt = Some(Instant.parse("2016-12-30T14:58:24Z")),
        addressUuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4")
      ),
      Address(
        street = " ",
        city = "Seattle",
        uuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4"),
        insertedAt = Some(Instant.parse("2016-12-30T14:56:44Z")),
        updatedAt = Some(Instant.parse("2016-12-30T14:56:44Z"))
      )
      ),
    (
      Person(
        name = "Matti Meikäläinen",
        age = 28,
        uuid = Some("b3f422f3-8aed-43a2-a52a-35fc0d41deae"),
        insertedAt = Some(Instant.parse("2016-12-30T15:03:49Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:03:49Z")),
        addressUuid = Some("0c4da69a-4da3-4481-8356-61398e5c729a")
      ),
      Address(
        street = " ",
        city = "Helsinki",
        uuid = Some("0c4da69a-4da3-4481-8356-61398e5c729a"),
        insertedAt = Some(Instant.parse("2016-12-30T15:01:23Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:01:23Z"))
      )
      ),
    (
      Person(
        name = "Maija Meikäläinen",
        age = 27,
        uuid = Some("e4f716fc-8eda-428f-a3da-a25d9ffbe810"),
        insertedAt = Some(Instant.parse("2016-12-30T15:03:51Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:03:51Z")),
        addressUuid = Some("0c4da69a-4da3-4481-8356-61398e5c729a")
      ),
      Address(
        street = " ",
        city = "Helsinki",
        uuid = Some("0c4da69a-4da3-4481-8356-61398e5c729a"),
        insertedAt = Some(Instant.parse("2016-12-30T15:01:23Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:01:23Z"))
      )
      ),
    (
      Person(
        name = "János Minta",
        age = 35,
        uuid = Some("47321ad0-f1a9-4313-a09e-cead428fd8ba"),
        insertedAt = Some(Instant.parse("2016-12-30T15:06:45Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:06:45Z")),
        addressUuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3")
      ),
      Address(
        street = " ",
        city = "Budapest",
        uuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3"),
        insertedAt = Some(Instant.parse("2016-12-30T15:06:37Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:06:37Z"))
      )
      ),
    (
      Person(
        name = "Kata Minta",
        age = 38,
        uuid = Some("60b433e8-b3a4-44dc-97e1-13f8210465b3"),
        insertedAt = Some(Instant.parse("2016-12-30T15:06:47Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:06:47Z")),
        addressUuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3")
      ),
      Address(
        street = " ",
        city = "Budapest",
        uuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3"),
        insertedAt = Some(Instant.parse("2016-12-30T15:06:37Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:06:37Z"))
      )
      )
  )

  val clubHomes: Seq[club.Home] = Seq(
    (
      Address(
        street = " ",
        city = "Seattle",
        uuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4"),
        insertedAt = Some(Instant.parse("2016-12-30T14:56:44Z")),
        updatedAt = Some(Instant.parse("2016-12-30T14:56:44Z"))
      ),
      Seq(
        Person(
          name = "John Smith",
          age = 25,
          uuid = Some("3104e3b9-708d-403d-ba9d-d78cd866bef6"),
          insertedAt = Some(Instant.parse("2016-12-30T14:58:20Z")),
          updatedAt = Some(Instant.parse("2016-12-30T14:58:20Z")),
          addressUuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4")
        ),
        Person(
          name = "Jane Smith",
          age = 23,
          uuid = Some("7b92b4cb-256c-4ef3-8651-b4bd20692e66"),
          insertedAt = Some(Instant.parse("2016-12-30T14:58:24Z")),
          updatedAt = Some(Instant.parse("2016-12-30T14:58:24Z")),
          addressUuid = Some("70ee1994-41a1-4b7b-85b2-924ffc836cb4")
        )
      )
    ),
    (
      Address(
        street = " ",
        city = "Helsinki",
        uuid = Some("0c4da69a-4da3-4481-8356-61398e5c729a"),
        insertedAt = Some(Instant.parse("2016-12-30T15:01:23Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:01:23Z"))
      ),
      Seq(
        Person(
          name = "Matti Meikäläinen",
          age = 28,
          uuid = Some("b3f422f3-8aed-43a2-a52a-35fc0d41deae"),
          insertedAt = Some(Instant.parse("2016-12-30T15:03:49Z")),
          updatedAt = Some(Instant.parse("2016-12-30T15:03:49Z")),
          addressUuid = Some("0c4da69a-4da3-4481-8356-61398e5c729a")
        ),
        Person(
          name = "Maija Meikäläinen",
          age = 27,
          uuid = Some("e4f716fc-8eda-428f-a3da-a25d9ffbe810"),
          insertedAt = Some(Instant.parse("2016-12-30T15:03:51Z")),
          updatedAt = Some(Instant.parse("2016-12-30T15:03:51Z")),
          addressUuid = Some("0c4da69a-4da3-4481-8356-61398e5c729a")
        )
      )
    ),
    (
      Address(
        street = " ",
        city = "Budapest",
        uuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3"),
        insertedAt = Some(Instant.parse("2016-12-30T15:06:37Z")),
        updatedAt = Some(Instant.parse("2016-12-30T15:06:37Z"))
      ),
      Seq(
        Person(
          name = "János Minta",
          age = 35,
          uuid = Some("47321ad0-f1a9-4313-a09e-cead428fd8ba"),
          insertedAt = Some(Instant.parse("2016-12-30T15:06:45Z")),
          updatedAt = Some(Instant.parse("2016-12-30T15:06:45Z")),
          addressUuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3")
        ),
        Person(
          name = "Kata Minta",
          age = 38,
          uuid = Some("60b433e8-b3a4-44dc-97e1-13f8210465b3"),
          insertedAt = Some(Instant.parse("2016-12-30T15:06:47Z")),
          updatedAt = Some(Instant.parse("2016-12-30T15:06:47Z")),
          addressUuid = Some("2d2fc9bd-ecc5-4720-948e-6f732eb28ac3")
        )
      )
    )
  )

}
