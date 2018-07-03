package model.request

import com.pharbers.jsonapi.JsonapiRootObjectReader
import com.pharbers.jsonapi.model.RootObject

object asJsonApiRequestOpt {
    def asJsonApiRequest[T](obj: RootObject)
                           (implicit format: JsonapiRootObjectReader[requests[T]]): requests[T] = format.fromJsonapi(obj)
}
