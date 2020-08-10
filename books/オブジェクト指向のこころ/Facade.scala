trait Database {
    def getSomething(): String
}

trait FileSystem {
    def readSomething(): String
}

class ConcreteDatabase extends Database {
    override def getSomething(): String = "ConcreteDatabase#getSomething()"
}

class ConcreteFileSystem extends FileSystem {
    override def readSomething(): String = "ConcreteFileSystem#readSomething()"
}

class Facade extends Database with FileSystem {
    val concreteDatabase = new ConcreteDatabase()
    val concreteFileSystem = new ConcreteFileSystem()
    override def getSomething(): String = concreteDatabase.getSomething()
    override def readSomething(): String = concreteFileSystem.readSomething()
}


object Client {
    def main(args: Array[String]): Unit = {
        val facade = new Facade()
        println(facade.getSomething())
        println(facade.readSomething())
    }
}