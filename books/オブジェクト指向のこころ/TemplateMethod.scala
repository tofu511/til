// あくまでサンプルなので実際にDBに接続したりする部分は省略

trait QueryTemplate {
    def doQuery(): Unit
    protected def formatConnect(): String
    protected def formatSelect(): String
}

class OracleQueryTemplate extends QueryTemplate {
    override def doQuery(): Unit = {
        val db = formatConnect()
        println(db)
        // dbの接続をする

        val command = formatSelect()
        println(command)
        // select文を発行する

        // dbをクローズする
    }
    override protected def formatConnect(): String = "Oracle"
    override protected def formatSelect(): String = "Select from Oracle DB"
}

class MySQLQueryTemplate extends QueryTemplate {
    override def doQuery(): Unit = {
        val db = formatConnect()
        println(db)
        // dbの接続をする

        val command = formatSelect()
        println(command)
        // select文を発行する

        // dbをクローズする
    }
    override protected def formatConnect(): String = "MySQL"
    override protected def formatSelect(): String = "Select from MySQL"
}


object QueryController {
    def main(args: Array[String]): Unit = {
        val dbs = Seq(new OracleQueryTemplate(), new MySQLQueryTemplate())
        dbs.foreach(_.doQuery)
    }
}