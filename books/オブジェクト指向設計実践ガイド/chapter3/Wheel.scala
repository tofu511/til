case class Wheel(rim: Int, tire: Int, gear: Gear) {
	def diameter = rim + (tire * 2)
	def gearInches = gear.gearInches(diameter)
}
