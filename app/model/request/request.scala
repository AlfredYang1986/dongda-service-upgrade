//package entity.request
//
//import entity.auth.auth_email
//import io.circe.generic.JsonCodec
//import com.pharbers.jsonapi.{JsonapiRootObjectReader, JsonapiRootObjectWriter}
//import com.pharbers.jsonapi.model.JsonApiObject.{NumberValue, StringValue}
//import com.pharbers.jsonapi.model.{Attribute, Attributes, Links, RootObject}
//import com.pharbers.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
//import com.pharbers.pattern.entity.commonResult
//
//@JsonCodec
//case class request (id : Int,
//                    major : Int,
//                    minor : Int,
//                    auth : Option[auth_email]) extends commonResult
//
//trait requestJsonApiOpt {
//    implicit val requestJsonapiRootObjectWriter: JsonapiRootObjectWriter[request] = new JsonapiRootObjectWriter[request] {
//        override def toJsonapi(req : request) = {
//            RootObject(data = Some(ResourceObject(
//                `type` = "request",
//                id = Some(req.id.toString),
//                attributes = Some(List(
//                    Attribute("major", NumberValue(req.major)),
//                    Attribute("minor", NumberValue(req.minor))
//                )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
//        }
//    }
//
//    implicit val requestJsonapiRootObjectReader : JsonapiRootObjectReader[request] = new JsonapiRootObjectReader[request] {
//        override def fromJsonapi(rootObject: RootObject) : request = {
//            import entity.request.requestsJsonApiOpt.requestsJsonapiRootObjectReader
//            val lst = requestsJsonapiRootObjectReader.fromJsonapi(rootObject)
//            lst.reqs.head
//        }
//    }
//}
//
//object requestJsonApiOpt extends requestJsonApiOpt
