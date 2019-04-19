case class Wheel(rim: Int, tire: Int) {
	import scala.math

	def diameter = rim + (tire * 2)
	def circumference = diameter * math.Pi
}
