package entity

import services.Brick
import play.api.mvc.Request
import com.pharbers.macros._
import com.pharbers.jsonapi.model
import com.pharbers.macros.convert.jsonapi.JsonapiMacro._

case class auth_test()(implicit val request: Request[model.RootObject]) extends Brick {
    override val brick_name: String = "test"
    override var next_brick: String = ""

    var request_data: request = _
    var result_data: auth = _
    var token: String = ""

    override def prepare: Unit = {
        val token = request.headers.get("Authorization").getOrElse("")
        phLog(s"*** token: $token")
        request_data = formJsonapi[request](request.body)
        phLog(s"request_data = $request_data")
    }

    override def exec: Unit = {
        result_data = new auth()
    }

    override def done: Option[String] = {
        val bricks = List("test")
        val url = request.uri
        if(bricks.size == url.split("/").last.toInt + 1)
            None
        else
            Some("")
    }

    override def forwardTo(next_brick: String): Unit = {
        phLog(" 正在写入 token .....")
        result_data.token = "token_badclkjsdfakjl;dj"
        phLog("登录成功")
    }

    override def goback: model.RootObject = {
        val out_data = toJsonapi(result_data)
        phLog(s"out_data = $out_data")
        out_data
    }

}