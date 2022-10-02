package imdb.query.http.client.datasets

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.Source
import imdb.query.http.client.models.NameBasics

object NameBasicsDataset extends Dataset[NameBasics] {

  override def request: HttpRequest =
    imdbBasicRequest("https://datasets.imdbws.com/name.basics.tsv.gz")

  override def data(implicit ac: ActorSystem): Source[NameBasics, _] =
    Source.single(request)
      .via(imdbFileByteString)
      .via(dataToMap)
      .map(NameBasics.apply)
}
