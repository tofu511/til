// コードは雰囲気です
trait Component {
    def printTicket(): Unit
}

class SalesTicket extends Component {
    override def printTicket(): Unit = println("SalesTicket#printTicket")
}

abstract class TicketDecorator(component: Component) extends Component {
    override def printTicket(): Unit = component.printTicket()
}

class Header1(component: Component) extends TicketDecorator(component) {
    override def printTicket(): Unit = {
        println("Header1#printTicket")
        component.printTicket()
    }
}

class Header2(component: Component) extends TicketDecorator(component) {
    override def printTicket(): Unit = {
        println("Header2#printTicket")
        component.printTicket()
    }
}

class Footer1(component: Component) extends TicketDecorator(component) {
    override def printTicket(): Unit = {
        component.printTicket()
        println("Footer1#printTicket")
    }
}

class Footer2(component: Component) extends TicketDecorator(component) {
    override def printTicket(): Unit = {
        component.printTicket()
        println("Footer2#printTicket")
    }
}

object Client {
    def main(args: Array[String]): Unit = {
        // Componentを取得する部分はFactoryに委譲できる
        val header1_salesTicket_footer1: Component = new Header1(new Footer1(new SalesTicket()))
        header1_salesTicket_footer1.printTicket() // Header1 -> 伝票 -> Footer1の順序で出力される

        val salesTicker_footer1_footer2: Component = new Footer1(new Footer2(new SalesTicket()))
        salesTicker_footer1_footer2.printTicket() // 伝票 -> Footer1 -> Footer2の順序で出力される
    }
}