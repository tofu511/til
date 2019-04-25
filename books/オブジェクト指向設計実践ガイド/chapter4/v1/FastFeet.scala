// FastFeet社の例をコードにしてみる
// 図4.5

case class Trip(bicycles: Seq[Bicycle], mechanic: Mechanic) {
	def prepareTrip() = {
		bicycles.map { bike =>
			mechanic.cleanBicycle(bike)
			mechanic.pumpTires(bike)
			mechanic.lubeChain(bike)
			mechanic.checkBrakes(bike)
		}
	}
}

case class Mechanic() {
	def cleanBicycle(bike: Bicycle) = ???
	def pumpTires(bike: Bicycle) = ???
	def lubeChain(bike: Bicycle) = ???
	def checkBrakes(bike: Bicycle) = ???
}

case class Bicycle()