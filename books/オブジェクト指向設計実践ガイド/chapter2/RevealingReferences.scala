class RevealingReferences(data: Seq[Seq[Int]]) {
	val wheels = wheelify(data)
	
	def diameters = {
		wheels.map { wheel => 
			wheel.rim + (wheel.tire * 2)
		}
	}

	def wheelify(data: Seq[Seq[Int]]) = {
		data.map { cell =>
			Wheel(cell(0), cell(1))
		}
	}
}

case class Wheel(rim: Int, tire: Int)
