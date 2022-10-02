package imdb.query.http.client.datasets
import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.Source
import imdb.query.http.client.models.TitleBasics

object TitleBasicsDataset extends Dataset[TitleBasics] {
  override def request: HttpRequest =
    imdbBasicRequest("https://datasets.imdbws.com/title.basics.tsv.gz")

  override def data(implicit ac: ActorSystem): Source[TitleBasics, _] =
    Source.single(request)
      .via(imdbFileByteString)
      .via(dataToMap)
      .map(TitleBasics.apply)
}
