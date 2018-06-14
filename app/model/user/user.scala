package model.user

import io.circe.generic.JsonCodec
import model.steps.commonresult

@JsonCodec case class user(id : String,
                           name: String,
                           photo : String,
                           auth : List[String]) extends commonresult
