package model.user

import io.circe.generic.JsonCodec
import com.pharbers.model.detail.commonresult
import com.pharbers.model._
import com.pharbers.jsonapi.model.JsonApiObject.{JsObjectValue, NumberValue}
import com.pharbers.jsonapi.JsonapiRootObjectFormat
import com.pharbers.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import com.pharbers.jsonapi.model.{Attribute, Links, RootObject}
import com.pharbers.macros.common.expending.Expandable
import com.pharbers.macros.common.expending.Expandable._
import com.pharbers.model.detail.user
import com.pharbers.model.detail.company

@JsonCodec
case class userdetailresult (id : String,
                             major : Int,
                             minor : Int,
                             user : Option[user],
                             company : Option[company]) extends commonresult with userdetailJsonApiOpt

//@TestAnnotation
trait userdetailJsonApiOpt extends JsonapiRootObjectFormat[userdetailresult] {

    override def toJsonapi(udr : userdetailresult) = {
        val tt = Expandable.materializeExpandable[com.pharbers.model.detail.user]
        val uu = tt.toJsonapi(udr.user.get)
        RootObject(data = Some(ResourceObject(
            `type` = "userdetailresult",
            id = Some(udr.id.toString),
            attributes = Some(List(
                Attribute("major", NumberValue(udr.major)),
                Attribute("minor", NumberValue(udr.minor)),
                Attribute("user", JsObjectValue(asJsonApi(udr.user.get))),
                Attribute("company", JsObjectValue(asJsonApi(udr.company.get)))
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
//        userdetailresult("123", 1, 1, Some(user(ObjectId.get.toString, "alfred yang", "photo", 1234, Nil)))
//        userdetailresult("123", 1, 1, Some(user(ObjectId.get.toString, "alfred yang", "photo", "1234", Nil)))
        null
    }
}

//object userdetailJsonApiOpt extends userdetailJsonApiOpt