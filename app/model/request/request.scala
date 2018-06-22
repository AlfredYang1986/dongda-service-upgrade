package model.request

import io.circe.generic.JsonCodec
import model.auth.auth_email
//import model.steps.commonresult
import com.pharbers.model.detail.commonresult
import com.pharbers.jsonapi.{JsonapiRootObjectReader, JsonapiRootObjectWriter}
import com.pharbers.jsonapi.model.JsonApiObject.{NumberValue, StringValue}
import com.pharbers.jsonapi.model.{Attribute, Attributes, Links, RootObject}
import com.pharbers.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}

@JsonCodec
case class request (id : Int,
                    major : Int,
                    minor : Int,
                    auth : Option[auth_email]) extends commonresult

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
            import model.request.requestsJsonApiOpt.requestsJsonapiRootObjectReader
            val lst = requestsJsonapiRootObjectReader.fromJsonapi(rootObject)
            lst.reqs.head
        }
    }
}

object requestJsonApiOpt extends requestJsonApiOpt
