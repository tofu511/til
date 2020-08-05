trait Subject {
    val observers = Seq.empty[Observer]
    def attach(obs: Observer): Seq[Observer] = observers :+ obs
    def detach(obs: Observer): Seq[Observer] = observers.filter(_ != obs)
    def publish(): Unit // notifyはscalaの組み込みメソッドと名前が被るのでpublishにする
}

trait Observer {
    def update(subject: Subject): Unit
}

class Customer(override val observers: Seq[Observer]) extends Subject {
    override def publish(): Unit = observers.foreach(_.update(this))
}

class AddrVerification extends Observer {
    override def update(myCustomer: Subject): Unit = {
        println("ここで住所確認をする")
    }
}

class WelcomeLetter extends Observer {
    override def update(myCustomer: Subject): Unit = {
        println("挨拶メールを送る")
    }
}

class ThankyouLetter extends Observer {
    override def update(subject: Subject): Unit = println("ありがとうメールを送る")
}


object Client {
    def main(args: Array[String]): Unit = {
        val addrVerification = new AddrVerification()
        val welcomeLetter = new WelcomeLetter()
        val customer1 = new Customer(Seq(addrVerification, welcomeLetter))
        customer1.publish() // ここで住所確認をする, 挨拶メールを送る
        val customer2 = new Customer(customer1.attach(new ThankyouLetter()))
        customer2.publish() // ここで住所確認をする, 挨拶メールを送る, ありがとうメールを送る
        val customer3 = new Customer(customer2.detach(addrVerification))
        customer3.publish() // 挨拶メールを送る, ありがとうメールを送る
    }
}