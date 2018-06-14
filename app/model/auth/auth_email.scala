package model.auth

import io.circe.generic.JsonCodec
import org.bson.types.ObjectId
import com.pharbers.jsonapi.{JsonapiRelationshipObjectReader, JsonapiResourceObjectReader}
import com.pharbers.jsonapi.model.JsonApiObject.StringValue
import com.pharbers.jsonapi.model.RootObject.{ResourceObject, ResourceObjects}
import com.pharbers.jsonapi.model.{Included, Relationship, Relationships, RootObject}

@JsonCodec case class auth_email(id : String,
                                 email : String,
                                 secret : Option[String],
                                 method : String = "email") extends auth

trait authEmailJsonApiOpt {
    implicit val authEmailJsonapiReader : JsonapiResourceObjectReader[auth_email] = new JsonapiResourceObjectReader[auth_email] {
        override def fromJsonapi(content : ResourceObject) : auth_email = {

            val id = content.id.get
            val (email, secret) = content.attributes.map { attr =>
                (attr.find("email" == _.name).map (_.value.asInstanceOf[StringValue].value).getOrElse(""),
                    attr.find("secret" == _.name).map (_.value.asInstanceOf[StringValue].value).getOrElse(""))
            }.getOrElse("", "")

            auth_email(id, email, Some(secret))
        }
    }

    implicit val authEmailJsonapiRelationReader : JsonapiRelationshipObjectReader[auth_email] = new JsonapiRelationshipObjectReader[auth_email] {
        override def fromJsonapi(ships : Relationships, included : Included) : auth_email = {
            ships.get("auth_email").get.data.
                map(_.asInstanceOf[ResourceObject] :: Nil).
                    getOrElse(ships.get("auth_email").get.data.
                        map(_.asInstanceOf[ResourceObjects].array.toList).
                        getOrElse(throw new Exception("error"))).
            map { ship =>
                val t = ship.`type`
                val id = ship.id.get

                val content = included.resourceObjects.array.toList.filter(p => p.id.get == id && p.`type` == t).head
                authEmailJsonapiReader.fromJsonapi(content)
            }.head
        }
    }
}

object authEmailJsonApiOpt extends authEmailJsonApiOpt