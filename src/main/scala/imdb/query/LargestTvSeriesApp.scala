package imdb.query

import akka.actor.{ActorSystem, Scheduler}
import imdb.query.api.IMDBMovieService

import scala.concurrent.ExecutionContextExecutor

object LargestTvSeriesApp extends App {
  implicit val system: ActorSystem = ActorSystem("LargestTvSeriesApp")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val scheduler: Scheduler = system.scheduler

  new IMDBMovieService().tvSeriesWithGreatestNumberOfEpisodes()
      .runForeach(println)
      .onComplete(_ => system.terminate())
}
