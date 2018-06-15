package model.user

import com.pharbers.macros.common.expending.{Expandable, TestAnnotation}
import io.circe.generic.JsonCodec
import model.steps.commonresult

@JsonCodec case class user(id : String,
                           name: String,
                           photo : String,
                           screen_name : String,
                           final_test : java.lang.Integer,
                           auth : List[String]) extends commonresult //with userdetailJsonApiOpt

@TestAnnotation
class userJsonApiOpt extends Expandable[model.user.user] {
    import model.user.user
    import com.pharbers.jsonapi.model.{Attribute, Attributes}
    import com.pharbers.jsonapi.model.RootObject.ResourceObject
    import com.pharbers.jsonapi.model.JsonApiObject.StringValue
    import com.pharbers.jsonapi.model.JsonApiObject.NumberValue
    import com.pharbers.jsonapi.model.JsonApiObject.NullValue
    import scala.reflect.ClassTag

    import scala.reflect.runtime.universe._
    import scala.reflect.runtime.{universe => ru}

//    override def toJsonapi(p : T)(implicit tag : ClassTag[T]) = {
    override def toJsonapi(p : model.user.user) = {

        val mirror = ru.runtimeMirror(getClass.getClassLoader)
        val inst_mirror = mirror.reflect(p)
        val class_symbol = inst_mirror.symbol
        val class_field = class_symbol.typeSignature.members.filter(p => p.isTerm && ! p.isMethod).toList

        class_field.map { f =>
            val attr_mirror = inst_mirror.reflectField(f.asTerm)
            val attr_val = attr_mirror.get
            Attribute(f.name.toString,
                if (f.info =:= typeOf[String]) StringValue(attr_val.toString)
                else if (f.info <:< typeOf[Number]) NumberValue(BigDecimal(attr_val.asInstanceOf[Number].doubleValue))
                else NullValue)
        }.filterNot(it => NullValue == it.value)
            .asInstanceOf[Attributes]
    }

    override def fromJsonapi(obj: ResourceObject) : model.user.user = ???
}

object userJsonApiOpt extends userJsonApiOpt
