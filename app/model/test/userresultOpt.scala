package model.test

import com.pharbers.jsonapi.model.JsonApiObject.{JsObjectValue, NullValue, NumberValue, StringValue}
import com.pharbers.jsonapi.model.{Attribute, Attributes, Links, RootObject}
import com.pharbers.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import com.pharbers.macros.common.TestAnnotation
import com.pharbers.macros.common.expending.Expandable
import com.pharbers.macros.common.resulting.Resultable
import com.pharbers.model._
import com.pharbers.model.detail._

import scala.reflect.runtime.universe._
import scala.reflect.runtime.{universe => ru}

@TestAnnotation
object userdetailJsonApiOpt extends Resultable[userdetailresult] {

    override def toJsonapi(p : userdetailresult) = {

        val mirror = ru.runtimeMirror(getClass.getClassLoader)
        val inst_mirror = mirror.reflect(p)
        val class_symbol = inst_mirror.symbol
        val class_field = class_symbol.typeSignature.members.filter(p => p.isTerm && ! p.isMethod).toList

        val companion_symbol = class_symbol.companion.asModule
        val companion_mirror = mirror.reflectModule(companion_symbol)
        val companion_instance = mirror.reflect(companion_mirror.instance)

        val opt = typeOf[Option[_]].typeSymbol
        def isConnectionInject(f : ru.Symbol) =
            f.info.baseType(opt) != NoType &&
                f.info.typeArgs.length == 1 &&
                f.info.typeArgs.head.baseClasses.
                    map(_.name.toString).contains("commonresult")

        val attrs =
            class_field.map { f =>
                val attr_mirror = inst_mirror.reflectField(f.asTerm)
                val attr_val = attr_mirror.get

                Attribute(f.name.toString,
                    if (f.info =:= typeOf[String]) StringValue(attr_val.toString)
                    else if (f.info <:< typeOf[Number]) NumberValue(BigDecimal(attr_val.asInstanceOf[Number].doubleValue))
                    else if (isConnectionInject(f)) {
                        val companion_implicit =
                            companion_symbol.typeSignature.members.
                                find(p => p.name.toString == f.name.toString).
                                map (x => x).getOrElse(throw new Exception(""))
//                        println(companion_implicit)

                        val compaion_field_mirror = companion_instance.reflectField(companion_implicit.asTerm)
//                        println(compaion_field_mirror)
//                        println(compaion_field_mirror.get)

//                        val tpr =
                        attr_val match {
                            case Some(x) => JsObjectValue(asJsonApi(x)(compaion_field_mirror.get.asInstanceOf[Expandable[Any]]))
                            case _ => ???
                        }
//                        println(tpr)
//                        tpr
                    }
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
