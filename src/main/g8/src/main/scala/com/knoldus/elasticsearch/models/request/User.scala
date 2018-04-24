package com.knoldus.elasticsearch.models.request

import play.api.libs.json.{Format, Json}

case class User(id: String, name: String, age: Int)

object User {
  implicit val format: Format[User] = Json.format[User]
}
