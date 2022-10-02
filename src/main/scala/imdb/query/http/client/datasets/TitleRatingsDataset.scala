package imdb.query.http.client.datasets
import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.Source
import imdb.query.http.client.models.TitleRatings

object TitleRatingsDataset extends Dataset[TitleRatings] {
  override def request: HttpRequest =
    imdbBasicRequest("https://datasets.imdbws.com/title.ratings.tsv.gz")

  override def data(implicit ac: ActorSystem): Source[TitleRatings, _] =
    Source.single(request)
      .via(imdbFileByteString)
      .via(dataToMap)
      .map(TitleRatings.apply)
}
