package imdb.query.http.client.models

import imdb.query.http.client.parsing.ParsedMap

/*
nconst (string) - alphanumeric unique identifier of the name/person
primaryName (string)– name by which the person is most often credited
birthYear – in YYYY format
deathYear – in YYYY format if applicable, else '\N'
primaryProfession (array of strings)– the top-3 professions of the person
knownForTitles (array of tconsts) – titles the person is known for
 */

final case class NameBasics(
                             nconst: String,
                             primaryName: String,
                             birthYear: Option[Int],
                             deathYear: Option[Int],
                             primaryProfession: List[String],
                             knownFor: List[String]
                           )

object NameBasics {
  def apply(parsed: Map[String, String]): NameBasics =
    NameBasics(
      nconst = parsed("nconst"),
      primaryName = parsed("primaryName"),
      birthYear = parsed.getIntOption("birthYear"),
      deathYear = parsed.getIntOption("deathYear"),
      primaryProfession = parsed.getStringList("primaryProfession"),
      knownFor = parsed.getStringList("knownFor")
    )



}
