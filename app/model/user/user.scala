package model.user

import com.mongodb.casbah.Imports._
import io.circe.generic.JsonCodec
import org.zalando.jsonapi.model.RootObject.ResourceObjects
//import io.circe.generic.auto._
//import io.circe.syntax._

import org.zalando.jsonapi.{JsonapiRootObjectReader, JsonapiRootObjectWriter}
import org.zalando.jsonapi.model.JsonApiObject.StringValue
import org.zalando.jsonapi.model.{Attribute, Links, RootObject}
import org.zalando.jsonapi.model.RootObject.ResourceObject
import org.bson.types.ObjectId

@JsonCodec case class user(id : String,
                           name: String,
                           photo : String,
                           auth : List[String])

trait userJsonApiOpt {
    implicit val userJsonapiRootObjectWriter: JsonapiRootObjectWriter[user] = new JsonapiRootObjectWriter[user] {
        override def toJsonapi(person: user) = {
            RootObject(data = Some(ResourceObject(
                `type` = "user",
                id = Some(person.id.toString),
                attributes = Some(List(
                    Attribute("name", StringValue(person.name)),
                    Attribute("photo", StringValue(person.photo))
                )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
        }
    }

    implicit val userJsonapiRootObjectReader : JsonapiRootObjectReader[user] = new JsonapiRootObjectReader[user] {
        override def fromJsonapi(rootObject: RootObject): user = {
            println(rootObject)
            println(rootObject.data.head)
            val tmp = rootObject.data.
                        map(_.asInstanceOf[ResourceObject] :: Nil).
                            getOrElse(rootObject.data.
                                map(_.asInstanceOf[ResourceObjects].array.toList).
                                    getOrElse(throw new Exception("error")))

            println(tmp)
            tmp.map { iter =>
                println(iter.`type`)
            }
            user(ObjectId.get.toString, "alfred yang", "photo", Nil)
        }
    }
}

object userJsonApiOpt extends userJsonApiOpt
