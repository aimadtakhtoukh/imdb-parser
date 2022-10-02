package imdb.query.http.client.datasets

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.alpakka.csv.scaladsl.{CsvParsing, CsvToMap}
import akka.stream.scaladsl.{Compression, Flow, Source}
import akka.util.ByteString

trait Dataset[T] {

  def imdbBasicRequest(uri: String): HttpRequest =
    HttpRequest(
      method = HttpMethods.GET,
      uri = uri
    )

  def imdbFileByteString(implicit ac: ActorSystem): Flow[HttpRequest, ByteString, NotUsed] =
    Flow[HttpRequest]
      .mapAsyncUnordered(1)(Http().singleRequest(_))
      .map(_.entity)
      .mapAsyncUnordered(1)(Unmarshal(_).to[ByteString])
      .via(Compression.gunzip())

  def request: HttpRequest

  def dataToMap(implicit ac: ActorSystem): Flow[ByteString, Map[String, String], _] =
    Flow[ByteString]
      .via(
        CsvParsing.lineScanner(
          delimiter = CsvParsing.Tab,
          quoteChar = '\'',
          maximumLineLength = 100 * 1024
        )
       )
      .via(CsvToMap.toMapAsStrings())


  def data(implicit ac: ActorSystem): Source[T, _]

}
