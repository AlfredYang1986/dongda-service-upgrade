package model.user

import com.pharbers.jsonapi.model.JsonApiObject.NumberValue
import com.pharbers.jsonapi.model.{Attribute, Links, RootObject}
import com.pharbers.jsonapi.model.RootObject.ResourceObject
import com.pharbers.macros.common.TestAnnotation
import com.pharbers.macros.common.resulting.Resultable
import com.pharbers.model.detail.userdetailresult

@TestAnnotation
class userdetailJsonApiOpt extends Resultable[userdetailresult] {

    override def toJsonapi(udr : userdetailresult) = {
        RootObject(data = Some(ResourceObject(
            `type` = "userdetailresult",
            id = Some(udr.id.toString),
            attributes = Some(List(
                Attribute("major", NumberValue(BigDecimal(udr.major))),
                Attribute("minor", NumberValue(BigDecimal(udr.minor)))
            )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
    }

    override def fromJsonapi(rootObject: RootObject): userdetailresult = ???
}

//object userdetailJsonApiOpt extends userdetailJsonApiOpt