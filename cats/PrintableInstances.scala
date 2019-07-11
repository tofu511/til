object PrintableInstances {
	implicit val stringPrinter: Printable[String] =
		new Printable[String] {
			def format(value: String): String = value
		}
	
	implicit val intPrinter: Printable[Int] =
		new Printable[Int] {
			def format(value: Int): String = value.toString
		}
	
	implicit val catPrinter: Printable[Cat] =
		new Printable[Cat] {
			def format(value: Cat): String = s"${value.name} is a ${value.age} year-old ${value.color} cat."
		}
}
