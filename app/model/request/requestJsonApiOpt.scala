package model.request

import com.pharbers.jsonapi.{JsonapiRelationshipObjectReader, JsonapiRootObjectReader, JsonapiRootObjectWriter}
import com.pharbers.jsonapi.model.JsonApiObject.NumberValue
import com.pharbers.jsonapi.model.RootObject.ResourceObject
import com.pharbers.jsonapi.model.{Attribute, Links, RootObject}

object requestJsonApiOpt extends requestJsonApiOpt

trait requestJsonApiOpt {

    implicit def requestToJsonapi[T] = new JsonapiRootObjectWriter[request[T]] {
        override def toJsonapi(req: request[T]) = {
            RootObject(data = Some(ResourceObject(
                `type` = "request",
                id = Some(req.id.toString),
                attributes = Some(List(
                    Attribute("major", NumberValue(req.major)),
                    Attribute("minor", NumberValue(req.minor))
                )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
        }
    }

    implicit def requestFromJsonapi[T](implicit rsr: JsonapiRelationshipObjectReader[T]) =
        new JsonapiRootObjectReader[request[T]] {
            override def fromJsonapi(rootObject: RootObject): request[T] = {
                import model.request.requestsJsonApiOpt.requestsFromJsonapi
                val lst = requestsFromJsonapi.fromJsonapi(rootObject)
                lst.reqs.head
            }
        }
}