package model.request

import com.pharbers.jsonapi.model.RootObject
import com.pharbers.jsonapi.model.JsonApiObject.NumberValue
import com.pharbers.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import com.pharbers.jsonapi.{JsonapiRelationshipObjectReader, JsonapiRootObjectReader}

object requestsJsonApiOpt extends requestsJsonApiOpt

trait requestsJsonApiOpt {
    implicit def requestsToJsonapi[T](req: requests[T]): RootObject = ???

    implicit def requestsFromJsonapi[T](implicit rsr: JsonapiRelationshipObjectReader[T]) =
        new JsonapiRootObjectReader[requests[T]] {
            override def fromJsonapi(rootObject: RootObject): requests[T] = {
                requests[T] {
                    rootObject.data.map(_.asInstanceOf[ResourceObject] :: Nil)
                            .getOrElse(
                                rootObject.data.map(_.asInstanceOf[ResourceObjects].array.toList)
                                        .getOrElse(throw new Exception("error"))
                            )
                            .map { iter =>
                                val (major, minor) = iter.attributes.map { attr =>
                                    (attr.find("major" == _.name).map(_.value.asInstanceOf[NumberValue].value.toInt).getOrElse(0),
                                            attr.find("minor" == _.name).map(_.value.asInstanceOf[NumberValue].value.toInt).getOrElse(0))
                                }.getOrElse(1, 1)

                                request[T](iter.id.get.toInt, major, minor, Some(rsr.fromJsonapi(iter.relationships.get, rootObject.included.get)))
                            }
                }
            }
        }
}
