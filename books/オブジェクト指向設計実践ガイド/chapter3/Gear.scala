case class Gear(chainring: Int, cog: Int) {
	def gearInches(diameter: Int) = ratio * diameter
	def ratio = chainring / cog.toFloat
}
