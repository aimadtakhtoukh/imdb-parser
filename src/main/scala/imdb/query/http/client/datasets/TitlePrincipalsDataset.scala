package imdb.query.http.client.datasets

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.Source
import imdb.query.http.client.models.TitlePrincipals

object TitlePrincipalsDataset extends Dataset[TitlePrincipals] {
  override def request: HttpRequest =
    imdbBasicRequest("https://datasets.imdbws.com/title.principals.tsv.gz")

  override def data(implicit ac: ActorSystem): Source[TitlePrincipals, _] =
    Source.single(request)
      .via(imdbFileByteString)
      .via(dataToMap)
      .map(TitlePrincipals.apply)
}
