// この場合、Bicycleは一般的な自転車とロードバイク固有の振る舞いを併せ持ってしまう
class MountainBike(style: String, size: String, tapeColor: String, frontShock: String, rearShock: String) extends Bicycle(style, size, tapeColor, frontShock, rearShock){
	def spares = ???
}
