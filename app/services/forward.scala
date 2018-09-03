package services

import com.pharbers.http.{HTTP, httpOpt}

object forward {
    def apply(host: String, port: String)(api: String): httpOpt =
        HTTP(s"http://$host:$port$api")
                .header("Accept" -> "application/json", "Content-Type" -> "application/json")
}
