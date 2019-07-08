# Type Class(型クラス
## Type Classとは？
- 型クラスは実装したい機能を表すインターフェースまたはAPIのこと
- Catsでは型クラスは最低１つの型パラメータをもつtraitとして表現される
```scala
sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}

```

### Type Class Instances(型クラスインスタンス)
- 型クラスインスタンスは型の実装を提供する
- Scalaでは型クラスの具体的な実装を作成し、implicitでタグ付けすることでインスタンスを定義する
```scala
final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json = 
        JsString(value)
    }
    
  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
    }
}
```

### Type Class Interfaces(型クラスインターフェース)
- 型クラスインターフェースはユーザーに公開するあらゆる機能のこと
- インターフェースはimplicit parameterとして型クラスインスタンスを受け取るメソッド
- インターフェースを指定する方法には2種類ある
  - Interface Objects
  - Interface Syntax

#### Interface Objects
```scala
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = w.write(value)
}

// 呼び出し側
import JsonWriterInstances._

Json.toJson(Person("Dave", "dave@example.com"))
```

#### Interface Syntax
- extension methods(拡張メソッド)を使うことで既存の型をインターフェースメソッドで拡張することができる
- Catsではこれを型クラスの構文(Syntax)と呼んでいる
```scala
object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json = w.write(value)
  }
}

// 呼び出し側
import JsonWriterInstances._
import JsonSyntax._

Person("dave", "dave@example.com").toJson
```

#### implicitly Method
- Scalaでは汎用型クラスインターフェースとして`implicitly`というものを標準ライブラリで提供している
```scala
def implicitly[A](implicit value: A): A = value
```
- implicitのスコープから任意の値を呼び出すために暗黙的に使用できる
```scala
import JsonWriterInstances._

implicitly[JsonWriter[String]] // JsonWriter[String]型のJsonWriterInsntacesが返る
```


