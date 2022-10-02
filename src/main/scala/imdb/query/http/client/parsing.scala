package imdb.query.http.client

object parsing {

  implicit class ParsedMap(val s: Map[String, String]) {

    val emptyValue: String = "\\N"

    def getIntOption(key: String): Option[Int] =
      s.get(key)
        .filter(_ != emptyValue)
        .flatMap(_.toIntOption)

    def getInt(key: String): Int =
      s.get(key)
        .filter(_ != emptyValue)
        .flatMap(_.toIntOption)
        .getOrElse(0)

    def getDouble(key: String): Double =
      s.get(key)
        .filter(_ != emptyValue)
        .flatMap(_.toDoubleOption)
        .getOrElse(0.0)

    def getStringList(key: String): List[String] =
      s.get(key)
        .filter(_ != emptyValue)
        .map(_.split(",").toList)
        .getOrElse(List.empty[String])

    def getStringOption(key: String): Option[String] =
      s.get(key)
        .filter(_ != emptyValue)

    def getBoolean(key: String): Boolean =
      s.get(key)
        .filter(_ != emptyValue)
        .contains("1")

  }
}
