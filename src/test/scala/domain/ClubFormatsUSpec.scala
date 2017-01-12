package domain

import contexts.ClubSpec
import org.specs2.specification.core.Env
import play.api.libs.json.Json
import scopes.WithClubData

class ClubFormatsUSpec(env : Env) extends ClubSpec {

  implicit val ee = env.executionEnv
  implicit val ec = env.executionContext

  "Marshalling" should {

    "marshal member collection" in new WithClubData {

      // Act

      val result = Json.toJson(clubMembers)

      // Assert

      result.toString mustEqual """[{"name":"John Smith","age":25,"uuid":"3104e3b9-708d-403d-ba9d-d78cd866bef6","insertedAt":"2016-12-30T14:58:20Z","updatedAt":"2016-12-30T14:58:20Z","address":{"street":" ","city":"Seattle","uuid":"70ee1994-41a1-4b7b-85b2-924ffc836cb4","insertedAt":"2016-12-30T14:56:44Z","updatedAt":"2016-12-30T14:56:44Z"}},{"name":"Jane Smith","age":23,"uuid":"7b92b4cb-256c-4ef3-8651-b4bd20692e66","insertedAt":"2016-12-30T14:58:24Z","updatedAt":"2016-12-30T14:58:24Z","address":{"street":" ","city":"Seattle","uuid":"70ee1994-41a1-4b7b-85b2-924ffc836cb4","insertedAt":"2016-12-30T14:56:44Z","updatedAt":"2016-12-30T14:56:44Z"}},{"name":"Matti Meikäläinen","age":28,"uuid":"b3f422f3-8aed-43a2-a52a-35fc0d41deae","insertedAt":"2016-12-30T15:03:49Z","updatedAt":"2016-12-30T15:03:49Z","address":{"street":" ","city":"Helsinki","uuid":"0c4da69a-4da3-4481-8356-61398e5c729a","insertedAt":"2016-12-30T15:01:23Z","updatedAt":"2016-12-30T15:01:23Z"}},{"name":"Maija Meikäläinen","age":27,"uuid":"e4f716fc-8eda-428f-a3da-a25d9ffbe810","insertedAt":"2016-12-30T15:03:51Z","updatedAt":"2016-12-30T15:03:51Z","address":{"street":" ","city":"Helsinki","uuid":"0c4da69a-4da3-4481-8356-61398e5c729a","insertedAt":"2016-12-30T15:01:23Z","updatedAt":"2016-12-30T15:01:23Z"}},{"name":"János Minta","age":35,"uuid":"47321ad0-f1a9-4313-a09e-cead428fd8ba","insertedAt":"2016-12-30T15:06:45Z","updatedAt":"2016-12-30T15:06:45Z","address":{"street":" ","city":"Budapest","uuid":"2d2fc9bd-ecc5-4720-948e-6f732eb28ac3","insertedAt":"2016-12-30T15:06:37Z","updatedAt":"2016-12-30T15:06:37Z"}},{"name":"Kata Minta","age":38,"uuid":"60b433e8-b3a4-44dc-97e1-13f8210465b3","insertedAt":"2016-12-30T15:06:47Z","updatedAt":"2016-12-30T15:06:47Z","address":{"street":" ","city":"Budapest","uuid":"2d2fc9bd-ecc5-4720-948e-6f732eb28ac3","insertedAt":"2016-12-30T15:06:37Z","updatedAt":"2016-12-30T15:06:37Z"}}]"""

    }

    "marshal home collection" in new WithClubData {

      // Act

      val result = Json.toJson(clubHomes)

      // Assert

      result.toString mustEqual """[{"street":" ","city":"Seattle","uuid":"70ee1994-41a1-4b7b-85b2-924ffc836cb4","insertedAt":"2016-12-30T14:56:44Z","updatedAt":"2016-12-30T14:56:44Z","persons":[{"name":"John Smith","age":25,"uuid":"3104e3b9-708d-403d-ba9d-d78cd866bef6","insertedAt":"2016-12-30T14:58:20Z","updatedAt":"2016-12-30T14:58:20Z"},{"name":"Jane Smith","age":23,"uuid":"7b92b4cb-256c-4ef3-8651-b4bd20692e66","insertedAt":"2016-12-30T14:58:24Z","updatedAt":"2016-12-30T14:58:24Z"}]},{"street":" ","city":"Helsinki","uuid":"0c4da69a-4da3-4481-8356-61398e5c729a","insertedAt":"2016-12-30T15:01:23Z","updatedAt":"2016-12-30T15:01:23Z","persons":[{"name":"Matti Meikäläinen","age":28,"uuid":"b3f422f3-8aed-43a2-a52a-35fc0d41deae","insertedAt":"2016-12-30T15:03:49Z","updatedAt":"2016-12-30T15:03:49Z"},{"name":"Maija Meikäläinen","age":27,"uuid":"e4f716fc-8eda-428f-a3da-a25d9ffbe810","insertedAt":"2016-12-30T15:03:51Z","updatedAt":"2016-12-30T15:03:51Z"}]},{"street":" ","city":"Budapest","uuid":"2d2fc9bd-ecc5-4720-948e-6f732eb28ac3","insertedAt":"2016-12-30T15:06:37Z","updatedAt":"2016-12-30T15:06:37Z","persons":[{"name":"János Minta","age":35,"uuid":"47321ad0-f1a9-4313-a09e-cead428fd8ba","insertedAt":"2016-12-30T15:06:45Z","updatedAt":"2016-12-30T15:06:45Z"},{"name":"Kata Minta","age":38,"uuid":"60b433e8-b3a4-44dc-97e1-13f8210465b3","insertedAt":"2016-12-30T15:06:47Z","updatedAt":"2016-12-30T15:06:47Z"}]}]"""

    }

  }
}