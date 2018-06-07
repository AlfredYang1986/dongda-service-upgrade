package model.user

import akka.stream.Attributes
import com.mongodb.casbah.Imports._
import io.circe.generic.JsonCodec
import model.steps.commonresult
import org.zalando.jsonapi.JsonapiReourceObjectWriter
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
                           auth : List[String]) extends commonresult

trait userJsonApiOpt {
    implicit val userJsonapiRootObjectWriter: JsonapiReourceObjectWriter[user] = new JsonapiReourceObjectWriter[user] {
        override def toJsonapi(person: user) = {
            Attribute("name", StringValue(person.name)) ::
            Attribute("photo", StringValue(person.photo)) ::
            Nil
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
