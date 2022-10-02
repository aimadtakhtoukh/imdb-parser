package imdb.query.api

import akka.stream.scaladsl.Source
import imdb.query.api.MovieService.{Principal, TvSeries}

object MovieService {
  final case class Principal(
                              name: String,
                              birthYear: Option[Int],
                              deathYear: Option[Int],
                              profession: List[String])
  final case class TvSeries(
                             original: String,
                             startYear: Option[Int],
                             endYear: Option[Int],
                             genres: List[String])
}

trait MovieService {
  def principalsForMovieName(name: String): Source[Principal, _]
  def tvSeriesWithGreatestNumberOfEpisodes(): Source[TvSeries, _]
}
