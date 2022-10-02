package imdb.query.api

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}
import imdb.query.api.MovieService.{Principal, TvSeries}
import imdb.query.http.client.datasets.{NameBasicsDataset, TitleBasicsDataset, TitleEpisodesDataset, TitlePrincipalsDataset}
import imdb.query.http.client.models.{NameBasics, TitleBasics}

import scala.concurrent.{ExecutionContext, Future}

class IMDBMovieService(implicit ac: ActorSystem, ec: ExecutionContext) extends MovieService {

  override def principalsForMovieName(name: String): Source[MovieService.Principal, _] =
    TitleBasicsDataset.data
      .via(getIdFromTitleName(name))
      .mapAsync(4) { tconst => getPrincipalsIdsFromTitleId(tconst)}
      .flatMapConcat(getNameInfoFromIdList)
      .map(data =>
        Principal(
          name = data.primaryName,
          birthYear = data.birthYear,
          deathYear = data.deathYear,
          profession = data.primaryProfession
        )
      )

  // Get the first title with that name and ends the search then.
  private def getIdFromTitleName(name: String): Flow[TitleBasics, String, NotUsed] =
    Flow[TitleBasics]
      .filter(_.primaryTitle == name)
        .take(1)
        .map(_.tconst)

  // Get the principals for the movie id passed as parameter
  private def getPrincipalsIdsFromTitleId(tconst: String): Future[Seq[String]] =
    TitlePrincipalsDataset.data
      .takeWhile(c => c.tconst.compareTo(tconst) <= 0)
      .filter(_.tconst == tconst)
      .map(_.nconst)
      .runWith(Sink.seq)

  //Get name info from name basics IMDB api from the indices as a parameter
  private def getNameInfoFromIdList(nameIndices: Seq[String]): Source[NameBasics, _] =
    NameBasicsDataset.data
      .filter(nameInfo => nameIndices.contains(nameInfo.nconst))
      .take(nameIndices.size)

  override def tvSeriesWithGreatestNumberOfEpisodes(): Source[MovieService.TvSeries, _] = {
    Source.future(episodeCountFuture)
      .map( _.map{ case (titleId, _) => titleId })
      .flatMapConcat(getTitleInfoFromIdList)
      .map(t =>
        TvSeries(
          original = t.originalTitle,
          startYear = t.startYear,
          endYear = t.endYear,
          genres = t.genres
        )
      )

  }
  
  private def episodeCountFuture: Future[Seq[(String, Int)]] =
    TitleEpisodesDataset.data
      .map(_.parentTconst)
      .groupBy(5000000, identity)
      .map(_ -> 1)
      .reduce((parentId, count) => (parentId._1, parentId._2 + count._2))
      .mergeSubstreams
      .runWith(Sink.seq)
      .map(episodeCount => episodeCount.sortBy(-_._2).take(10))

  private def getTitleInfoFromIdList(titleIndices: Seq[String]): Source[TitleBasics, _] =
    TitleBasicsDataset.data
      .filter(titleInfo => titleIndices.contains(titleInfo.tconst))
      .take(titleIndices.size)
}
