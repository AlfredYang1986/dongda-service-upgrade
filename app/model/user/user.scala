package model.user

import io.circe.generic.JsonCodec
import model.steps.commonresult
import org.zalando.jsonapi.{JsonapiResourceObjectFormat}
//import io.circe.generic.auto._
//import io.circe.syntax._

import org.zalando.jsonapi.model.JsonApiObject.StringValue
import org.zalando.jsonapi.model.{Attribute}
import org.zalando.jsonapi.model.RootObject.ResourceObject
import org.bson.types.ObjectId

@JsonCodec case class user(id : String,
                           name: String,
                           photo : String,
                           auth : List[String]) extends commonresult with userdetailJsonApiOpt

trait userJsonApiOpt extends JsonapiResourceObjectFormat[user] {

    override def toJsonapi(p : user) = {
        Attribute("name", StringValue(p.name)) ::
        Attribute("photo", StringValue(p.photo)) ::
        Nil
    }

    override def fromJsonapi(rootObject: ResourceObject): user = {
        user(ObjectId.get.toString, "alfred yang", "photo", Nil)
    }
}

object userJsonApiOpt extends userJsonApiOpt
