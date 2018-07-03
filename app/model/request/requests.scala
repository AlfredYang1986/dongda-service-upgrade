package model.request

import io.circe.generic.JsonCodec
import com.pharbers.model.detail.commonresult

@JsonCodec
case class request[T] (id : Int,
                    major : Int,
                    minor : Int,
                    data : Option[T]) extends commonresult

case class requests[T](reqs: List[request[T]])

