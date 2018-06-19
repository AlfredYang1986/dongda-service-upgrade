package model.test

import com.pharbers.jsonapi.model.JsonApiObject.{JsObjectValue, NullValue, NumberValue, StringValue}
import com.pharbers.jsonapi.model.{Attribute, Attributes, Links, RootObject}
import com.pharbers.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import com.pharbers.macros.common.TestAnnotation
import com.pharbers.macros.common.resulting.Resultable
import com.pharbers.model.detail._

import scala.reflect.runtime.universe._
import scala.reflect.runtime.{universe => ru}

//Attribute("major", NumberValue(BigDecimal(udr.major))),
//Attribute("minor", NumberValue(BigDecimal(udr.minor))),
//Attribute("user", JsObjectValue(asJsonApi(udr.user.get))),
//Attribute("company", JsObjectValue(asJsonApi(udr.company.get)))

class userdetailJsonApiOpt extends Resultable[userdetailresult] {

    @TestAnnotation
    override def toJsonapi(p : userdetailresult) = {

        val mirror = ru.runtimeMirror(getClass.getClassLoader)
        val inst_mirror = mirror.reflect(p)
        val class_symbol = inst_mirror.symbol
        val class_field = class_symbol.typeSignature.members.filter(p => p.isTerm && ! p.isMethod).toList

        val attrs =
        class_field.map { f =>
            val attr_mirror = inst_mirror.reflectField(f.asTerm)
            val attr_val = attr_mirror.get
            Attribute(f.name.toString,
                if (f.info =:= typeOf[String]) StringValue(attr_val.toString)
                else if (f.info <:< typeOf[Number]) NumberValue(BigDecimal(attr_val.asInstanceOf[Number].doubleValue))
                else NullValue)
        }.filterNot(it => NullValue == it.value)
            .asInstanceOf[Attributes]

        RootObject(data = Some(ResourceObject(
            `type` = "userdetailresult",
            id = Some(p.id.toString),
            attributes = Some(
                attrs.toList
            ), links = Some(List(Links.Self("http://test.link/person/42", None))))))
    }

    override def fromJsonapi(rootObject: RootObject): userdetailresult = ???
}
