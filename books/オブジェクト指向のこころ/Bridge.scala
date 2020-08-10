trait Shape {
    val drawing: Drawing
    def draw(): Unit
}

class Rectange(override val drawing: Drawing) extends Shape {
    override def draw(): Unit = drawing.drawLine()
}

class Circle(override val drawing: Drawing) extends Shape {
    override def draw(): Unit = drawing.drawCircle()
}

trait Drawing {
    // 実際は描画のために必要な引数を取る
    def drawLine(): Unit
    def drawCircle(): Unit
}

class V1Drawing extends Drawing {
    override def drawLine(): Unit = println("V1 drawLine")
    override def drawCircle(): Unit = println("V1 drawCircle")
}

class V2Drawing extends Drawing {
    override def drawLine(): Unit = println("V2 drawLine")
    override def drawCircle(): Unit = println("V2 drawCircle")
}


object Client {
    def main(args: Array[String]): Unit = {
        val shapes = Seq(
            new Rectange(new V1Drawing()),
            new Rectange(new V2Drawing()),
            new Circle(new V1Drawing()),
            new Circle(new V2Drawing())
        )
        shapes.foreach(_.draw())
    }
}