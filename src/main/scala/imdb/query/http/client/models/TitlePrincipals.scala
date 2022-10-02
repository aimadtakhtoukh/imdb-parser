package imdb.query.http.client.models

import imdb.query.http.client.parsing.ParsedMap

/*
tconst (string) - alphanumeric unique identifier of the title
ordering (integer) – a number to uniquely identify rows for a given titleId
nconst (string) - alphanumeric unique identifier of the name/person
category (string) - the category of job that person was in
job (string) - the specific job title if applicable, else '\N'
characters (string) - the name of the character played if applicable, else '\N'
 */

final case class TitlePrincipals(
                                  tconst: String,
                                  ordering: Int,
                                  nconst: String,
                                  category: String,
                                  job: Option[String],
                                  characters: Option[String]
                                )

object TitlePrincipals {
  def apply(parsed: Map[String, String]): TitlePrincipals =
    TitlePrincipals(
      tconst = parsed("tconst"),
      ordering = parsed.getInt("ordering"),
      nconst = parsed("nconst"),
      category = parsed("category"),
      job = parsed.getStringOption("job"),
      characters = parsed.getStringOption("characters")
    )
}