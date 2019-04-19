case class Gear(chainring: Int, cog: Int, wheel: Wheel) {
	def ratio = chainring / cog.toFloat
  	def gearInches = ratio * wheel.diameter
}
