package model

import com.mongodb.DBObject

import scala.reflect.runtime.universe._
import com.pharbers.jsonapi.model.JsonApiObject.{NumberValue, StringValue}
import com.pharbers.jsonapi.model.RootObject.ResourceObject
import com.pharbers.jsonapi.model.{Attribute, Links, RootObject}
import com.pharbers.macros.api.{JsonapiConvert, MongoDBConvert, commonEntity}

class user extends commonEntity {
    val id: String = ""
    val res: String = ""
}

object user {

    implicit object userMongoDB extends MongoDBConvert[user] {
        override def fromMongo(mongo: DBObject): user = ???

        override def toMongo(obj: user): DBObject = ???
    }

    implicit object userJsonapi extends JsonapiConvert[user] {
        override def fromJsonapi(jsonapi: RootObject): user = {
            val data = jsonapi.data.map(_.asInstanceOf[ResourceObject]).get
            val attrs = data.attributes.get.toList
            val included = jsonapi.included
            val id = data.id.get

            println(attrs)
            println(included)
            println(id)
//                val mirror = runtimeMirror(getClass.getClassLoader)
//                val inst_mirror = mirror.reflect(this)
//                val class_symbol = inst_mirror.symbol
//                val class_field = class_symbol.typeSignature.members.filter(p => p.isTerm && ! p.isMethod).toList
//                val values = class_field.map { x =>
//                    val name = x.name.toString.trim
//                    println(name)
//                    val tpe = x.info
//                    println(tpe)
//                    val value = attrs.find(y => y.name == name)
//                    name -> value.map(_.value.asInstanceOf[StringValue].value).getOrElse("")
//                }.toMap ++ Map("id" -> id)

//        val attrs = .map{ attr =>
//            attr.name -> attr.value
//        }
//                println(values)
            new user
        }

        override def toJsonapi(obj: user): RootObject = {
            RootObject(data = Some(ResourceObject(
                `type` = "request",
                id = Some(""),
                attributes = Some(List(
                    Attribute("major", NumberValue(1)),
                    Attribute("minor", NumberValue(2))
                )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
        }
    }
}
