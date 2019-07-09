# Type Class(型クラス)
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

## Implicits
- 型クラスを扱うにはimplicit valueとimplicit parametersを扱う必要がある

### Implicit Scope
- コンパイラは以下の範囲を暗黙的に探す
  - ローカルもしくは継承された定義
  - importされた定義
  - 型クラスまたはパラメータ型のコンパニオンオブジェクト
- コンパイラはimplicitが複数あるとコンパイルエラーを吐く
```scala
implicit val writer1: JsonWriter[String] = JsonWriterInstances.stringWriter
implicit val writer2: JsonWriter[String] = JsonWriterInstances.stringWriter

Json.toJson("A string")
// <console>:23: error: ambiguous implicit values:
// both value stringWriter in object JsonWriterInstances of type => JsonWriter[String]
// and value writer1 of type => JsonWriter[String]
// match expected type JsonWriter[String]
// Json.toJson("A string")
//            ^
```
- implicitなものをどこに定義するかはおおよそ4つの方法に分けられる
  1. JsonWrietrInstancesなどのオブジェクトに配置する => importすることでスコープに入る
  2. traitに入れる => 継承するとスコープに入る
  3. 型クラスのコンパニオンオブジェクトに入れる => 常にスコープに入る
  4. パラメータ型のコンパニオンオブジェクトに入れる => 常にスコープに入る

### Recursive Implicit Resolution
- 型クラスのインスタンスを定義する方法は2つ
  - `implicit val`を使う
  - `implicit def`を使う
- ただ`implicit val`ではスケールしない時がある
- 例として`JsonWriter[Option[A]]`を定義するとすると、全ての型に対して`implicit val`を作らないといけなくなる
```scala
implicit val optionIntWriter: JsonWriter[Option[Int]] = ???

implicit val optionPersonWriter: JsonWriter[Option[Person]] = ???

// 他にもたくさん必要。。
```
- `implicit def`を使うことで`Option[A]`を抽象的に定義することができる
```scala
implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] = 
  new JsonWriter[Option[A]] {
    def write(option: Option[A]): Json = option match {
      case Some(aValue) => writer.write(aValue)
      case None => JsNull
    }
  }
```
  
  

