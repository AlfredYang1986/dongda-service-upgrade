package model.request

import org.zalando.jsonapi.{JsonapiRootObjectReader, JsonapiRootObjectWriter}
import org.zalando.jsonapi.model.JsonApiObject.NumberValue
import org.zalando.jsonapi.model.{Attribute, Links, RootObject}
import org.zalando.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}

case class requests (reqs : List[request])

trait requestsJsonApiOpt {
    implicit val requestsJsonapiRootObjectWriter: JsonapiRootObjectWriter[requests] = new JsonapiRootObjectWriter[requests] {
        override def toJsonapi(req : requests) = {
//            RootObject(data = Some(ResourceObject(
//                `type` = "request",
//                id = Some(req.id.toString),
//                attributes = Some(List(
//                    Attribute("major", NumberValue(req.major)),
//                    Attribute("minor", NumberValue(req.minor))
//                )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
            null
        }
    }

    implicit val requestsJsonapiRootObjectReader : JsonapiRootObjectReader[requests] = new JsonapiRootObjectReader[requests] {
        override def fromJsonapi(rootObject: RootObject) : requests = {
            requests(
                rootObject.data.
                    map(_.asInstanceOf[ResourceObject] :: Nil).
                    getOrElse(rootObject.data.
                    map(_.asInstanceOf[ResourceObjects].array.toList).
                    getOrElse(throw new Exception("error"))).
                map { iter =>
                    val (major, minor) = iter.attributes.map { attr =>
                        (attr.find("major" == _.name).map (_.value.asInstanceOf[NumberValue].value.toInt).getOrElse(0),
                            attr.find("minor" == _.name).map (_.value.asInstanceOf[NumberValue].value.toInt).getOrElse(0))
                    }.getOrElse(1, 1)

                    import model.auth.authEmailJsonApiOpt.authEmailJsonapiRelationReader
                    request(iter.id.get.toInt, major, minor,
                        Some(authEmailJsonapiRelationReader.fromJsonapi(iter.relationships.get, rootObject.included.get)))
                }
            )
        }
    }
}

object requestsJsonApiOpt extends requestsJsonApiOpt