package imdb.query

import akka.actor.{ActorSystem, Scheduler}
import imdb.query.api.IMDBMovieService

import scala.concurrent.ExecutionContextExecutor

object PrincipalsForCollegeRhythm extends App {
  implicit val system: ActorSystem = ActorSystem("CastFromTitle")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val scheduler: Scheduler = system.scheduler

  new IMDBMovieService().principalsForMovieName("College Rhythm")
//  new IMDBMovieService().principalsForMovieName("Don't Worry Darling")
    .runForeach(println)
    .recover(_.printStackTrace())
    .onComplete(_ => system.terminate())
}
