package model.request

import io.circe.generic.JsonCodec
import model.auth.auth_email
import org.zalando.jsonapi.{JsonapiRootObjectReader, JsonapiRootObjectWriter}
import org.zalando.jsonapi.model.JsonApiObject.{NumberValue, StringValue}
import org.zalando.jsonapi.model.{Attribute, Links, RootObject}
import org.zalando.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import org.bson.types.ObjectId

@JsonCodec
case class request (id : Int,
                    major : Int,
                    minor : Int,
                    auth : Option[auth_email])

trait requestJsonApiOpt {
    implicit val requestJsonapiRootObjectWriter: JsonapiRootObjectWriter[request] = new JsonapiRootObjectWriter[request] {
        override def toJsonapi(req : request) = {
            RootObject(data = Some(ResourceObject(
                `type` = "request",
                id = Some(req.id.toString),
                attributes = Some(List(
                    Attribute("major", NumberValue(req.major)),
                    Attribute("minor", NumberValue(req.minor))
                )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
        }
    }

    implicit val requestJsonapiRootObjectReader : JsonapiRootObjectReader[request] = new JsonapiRootObjectReader[request] {
        override def fromJsonapi(rootObject: RootObject) : request = {
            val tmp = rootObject.data.
                map(_.asInstanceOf[ResourceObject] :: Nil).
                getOrElse(rootObject.data.
                    map(_.asInstanceOf[ResourceObjects].array.toList).
                    getOrElse(throw new Exception("error")))

            val lst =
                tmp.map { iter =>
                    val (major, minor) = iter.attributes.map { attr =>
                        (attr.find("major" == _.name).map (_.value.asInstanceOf[NumberValue].value.toInt).getOrElse(0),
                         attr.find("minor" == _.name).map (_.value.asInstanceOf[NumberValue].value.toInt).getOrElse(0))
                    }.getOrElse(1, 1)

                    import model.auth.authEmailJsonApiOpt.authEmailJsonapiRelationReader
                    request(iter.id.get.toInt, major, minor,
                        Some(authEmailJsonapiRelationReader.fromJsonapi(iter.relationships.get, rootObject.included.get)))
                }
            lst.head
        }
    }
}

object requestJsonApiOpt extends requestJsonApiOpt