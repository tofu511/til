// FastFeet社の例をコードにしてみる
// 図4.7

case class Trip(mechanic: Mechanic) {
	val bicycles = mechanic.prepareTrip(this)
	mechanic.prepareBicycle(bicycles)
}

case class Mechanic() {
	def prepareTrip(trip: Trip): Seq[Bicycle] = ???
	def prepareBicycle(bicycles: Seq[Bicycle]) = {
		bicycles.map { bike =>
			???
		}
	}
}

case class Bicycle()