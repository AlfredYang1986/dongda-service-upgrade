package services.AuthService

import javax.inject.Singleton
import model.user.user
import services.Service

trait AuthService extends Service[user]

@Singleton
class AuthServiceImpl extends AuthService {
    override def push(p: user): Option[String] = None

    override def pop(u: user): Option[String] = None

    override def update(c: user, u: user): Option[String] = None

    override def query(c: user): Option[String] = None

    override def queryMulti(c: user): Option[String] = None
}
