package imdb.query.http.client.models

import imdb.query.http.client.parsing.ParsedMap

/*
tconst (string) - alphanumeric identifier of episode
parentTconst (string) - alphanumeric identifier of the parent TV Series
seasonNumber (integer) – season number the episode belongs to
episodeNumber (integer) – episode number of the tconst in the TV series
 */

final case class TitleEpisodes(
                              tconst: String,
                              parentTconst: String,
                              seasonNumber: Int,
                              episodeNumber: Int
                              )

object TitleEpisodes {
  def apply(parsed: Map[String, String]): TitleEpisodes =
    TitleEpisodes(
      tconst = parsed("tconst"),
      parentTconst = parsed("parentTconst"),
      seasonNumber = parsed.getInt("seasonNumber"),
      episodeNumber = parsed.getInt("episodeNumber")
    )
}
