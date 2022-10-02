package imdb.query.http.client.datasets
import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.Source
import imdb.query.http.client.models.TitleEpisodes

object TitleEpisodesDataset extends Dataset[TitleEpisodes] {
  override def request: HttpRequest =
    imdbBasicRequest("https://datasets.imdbws.com/title.episode.tsv.gz")

  override def data(implicit ac: ActorSystem): Source[TitleEpisodes, _] =
    Source.single(request)
      .via(imdbFileByteString)
      .via(dataToMap)
      .map(TitleEpisodes.apply)
}
