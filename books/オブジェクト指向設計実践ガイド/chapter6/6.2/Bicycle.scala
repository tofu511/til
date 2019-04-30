class Bicycle(style: String, size: String, tapeColor: String, frontShock: String, rearShock: String) {


	// styleの確認は危険な道へ進む一歩
	def spares = {
		// 自身分類に基づきなんのメッセージ送るか決めている
		// この知識は依存を生み出し、変更コストをあげる
		if (style == "road") {
			Map(
				"chain" -> "10-speed", 
				"tireSize" -> "23",  // milimeters
				"tapeColor" -> tapeColor)
		} else {
			Map(
				"chain" -> "10-speed", 
				"tireSize" -> "2.1", // inches
				"rearShock" -> rearShock)
		}

	}
}