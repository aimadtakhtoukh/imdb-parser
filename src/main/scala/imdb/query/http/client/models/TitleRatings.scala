package imdb.query.http.client.models

import imdb.query.http.client.parsing.ParsedMap


/*
tconst (string) - alphanumeric unique identifier of the title
averageRating – weighted average of all the individual user ratings
numVotes - number of votes the title has received
 */

final case class TitleRatings(
                             tconst: String,
                             averageRating: Double,
                             numVotes: Int
                             )

object TitleRatings {
  def apply(parsed: Map[String, String]): TitleRatings =
    TitleRatings(
      tconst = parsed("tconst"),
      averageRating = parsed.getDouble("averageRating"),
      numVotes = parsed.getInt("numVotes")
    )
}