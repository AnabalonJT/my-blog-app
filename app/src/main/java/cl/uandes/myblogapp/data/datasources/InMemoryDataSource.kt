package cl.uandes.myblogapp.data.datasources

import cl.uandes.myblogapp.data.model.User

object InMemoryDataSource {
  var authToken: String? = null
  var email: String? = null
  var currentUser: User? = null
}
