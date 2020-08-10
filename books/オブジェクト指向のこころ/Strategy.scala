class SalesOrder(val taxCalculator: CalcTax)

trait CalcTax {
    def taxAmount(quantity: Int, price: Double): Double
}

class USTax extends CalcTax {
    override def taxAmount(quantity: Int, price: Double): Double = {
        // 実際はアメリカの税制に従う
        quantity * price * 1.5
    }
}

class CanadaTax extends CalcTax {
    override def taxAmount(quantity: Int, price: Double): Double = {
        // 実際はカナダの税制に従う
        quantity * price * 1.2
    }
}

object Client {
    def main(args: Array[String]): Unit = {
        val usOrder = new SalesOrder(new USTax())
        val us = usOrder.taxCalculator.taxAmount(10, 200)
        println(us)
        val canadaOrder = new SalesOrder(new CanadaTax())
        val canada = canadaOrder.taxCalculator.taxAmount(10, 200)
        println(canada)

    }
}