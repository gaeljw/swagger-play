package play.modules.swagger

import io.swagger.v3.oas.integration.api.OpenApiScanner
import io.swagger.v3.oas.models.OpenAPI
import javax.inject.Inject
import play.api.Logger

import scala.collection.mutable

class ApiListingCache @Inject()(scanner: OpenApiScanner, reader: PlayReader) {
  private val cache: mutable.Map[String, OpenAPI] = mutable.Map.empty

  def listing(host: String): OpenAPI = {
    cache.getOrElseUpdate(host, {
      Logger("swagger").debug("Loading API metadata")

      val classes = scanner.classes()
      val swagger = reader.read(classes)
//      val result = scanner match {
//        case config: SwaggerConfig =>
//          config.configure(swagger)
//        case _ =>
//          swagger
//      }
      swagger.setHost(host)
      swagger
    })
  }
}
