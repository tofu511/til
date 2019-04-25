// FastFeet社の例をコードにしてみる
// 図4.6

case class Trip(bicycles: Seq[Bicycle], mechanic: Mechanic) {
	def prepareTrip() = {
		bicycles.map { bike =>
			mechanic.prepareBicycle(bike)
		}
	}
}

case class Mechanic() {
	def prepareBicycle(bike: Bicycle) = {
		cleanBicycle(bike)
		???
	}

	private[this] def cleanBicycle(bike: Bicycle) = ???
}

case class Bicycle()