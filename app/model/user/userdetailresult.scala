package model.user

import io.circe.generic.JsonCodec
import model.steps.commonresult
import org.bson.types.ObjectId
import org.zalando.jsonapi.model.JsonApiObject.{JsObjectValue, NumberValue}
import org.zalando.jsonapi.{JsonapiRootObjectFormat}
import org.zalando.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import org.zalando.jsonapi.model.{Attribute, Links, RootObject}

@JsonCodec
case class userdetailresult (id : String,
                             major : Int,
                             minor : Int,
                             user : Option[user]) extends commonresult with userdetailJsonApiOpt

trait userdetailJsonApiOpt extends JsonapiRootObjectFormat[userdetailresult] {
    override def toJsonapi(udr : userdetailresult) = {
        RootObject(data = Some(ResourceObject(
            `type` = "userdetailresult",
            id = Some(udr.id.toString),
            attributes = Some(List(
                Attribute("major", NumberValue(udr.major)),
                Attribute("minor", NumberValue(udr.minor)),
                Attribute("user", JsObjectValue(userJsonApiOpt.toJsonapi(udr.user.get)))
            )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
    }

    override def fromJsonapi(rootObject: RootObject): userdetailresult = {
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
        userdetailresult("123", 1, 1, Some(user(ObjectId.get.toString, "alfred yang", "photo", Nil)))
    }
}

//object userdetailJsonApiOpt extends userdetailJsonApiOpt