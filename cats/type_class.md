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

## Meets Cats
- Catsにおける型クラス
- `Show`はCatsにおける`Printable`と同等の型クラス

```scala
package cats

trait Show[A] {
  def show(value: A): String
}
```

### Importing Type Class
- 型クラスそのもの(ここでいう`Show`)と実装をもつ型クラスインスタンスをインポートする必要がある

### Importing Default Instances
- `cats.instances`パッケージにデフォルト(組み込み型)のインスタンスがあるので、これらをインポートする
  - ex.
  - `cats.instances.int`
  - `cats.instances.list`
  - `cats.instances.option`
```scala
import cats.instances.int._    
import cats.instances.string._ 

val showInt: Show[Int] = Show.apply[Int] 
val showString: Show[String] = Show.apply[String]

val intAsString: String = showInt.show(123) // "123"
val stringAsString: String = showString.show("abc") // "abc"
```

### Importing Interface Syntax
- 型クラスの構文(Syntax)は`cats.syntax` パッケージにある
```scala
import cats.syntax.show._ 

val shownInt = 123.show // "123"
val shownString = "abc".show // "abc"
```

### Importing All The Things!
- 全てインポートするには以下のようにする
```scala
import cats._
import cats.implicits._
```

### Defining Custom Instances
- デフォルトインスタンスに無いクラスはカスタムのインスタンスを作ることで`Show`を使うことができる
```scala
import java.util.Date

implicit val dateShow: Show[Date] =
  new Show[Date] {
    def show(date: Date): String =
      s"${date.getTime}ms since the epoch."
}
```
- 上記のようにもできるがCatsでは簡単にカスタムインスタンスを作るためのメソッドがすでに準備されている
- `Show`のコンパニオンオブジェクトには2つのコンストラクションメソッドがあるので、それを使うことでカスタムインスタンスを作ることができる
```scala
object Show {
  // Convert a function to a `Show` instance:
  def show[A](f: A => String): Show[A] = ???
  
  // Create a `Show` instance from a `toString` method:
  def fromToString[A]: Show[A] = ???
}
```
- 上記のコンストラクションメソッドを使うと以下のようになる
```scala
implicit val dateShow: Show[Date] = Show.show(date => s"${date.getTime}ms since the epoch.")
```

## Controlling Instance Selection
- 型クラスを使う上で考えなければならないインスタンス選定(Instance Selection)の2つの課題がある
  1. インスタンスとして定義した型とその派生型(subtype)の関係性
    - ex. `JsonWriter[Option[Int]]` を定義した場合、`Json.toJson(Some(1))` が選択される(SomeはOptionの派生型(subtype))
  2. たくさんの選択肢がある中でどのように型クラスインスタンスを選択するか
    - ex. `JsonWriter`に対して2つの`Person`型を定義した時、どちらが選択されるか

### Variance(変位)
- 型クラスを定義する時、変位注釈(変位アノテーション)をつけることで、コンパイラが暗黙的にインスタンス選定(instance selection)できるようになる

#### Covariance(共変)
- 型パラメータに`+`をつけることで共変になる
- 共変とは、`B`が`A`の派生型(subtype)の場合、`F[B]`が`F[A]`も派生型(subtype)であるという性質
- 共変の場合、`F[A] = F[B]`という代入ができるようになる
- ListやOptionは共変の1つの例
```scala
trait List[+A]
trait Option[+A]
```
- Listは共変なので、`A`の派生型(subtype)であれば、代入することができる
```scala
sealed trait Shape
case class Circle(redius: Double) extends Shape

val circles: List[Circle] = ???
val shapes: List[Shape] = circles
```

##### Contracariance(反変)
- 型パラメータに`-`をつけることで反変になる
- 反変とは、`A`が`B`の派生型の場合、`F[B]`が`F[A]`の派生型であるという性質
```scala
trait JsonWriter[-A] {
  def write(value: A): Json
}
```
```scala
val shape: Shape = ???
val circle: Circle = ???

val shapeWriter: JsonWriter[Shape] = ???
val circleWriter: JsonWriter[Circle] = ???

def format[A](value: A, writer: JsonWriter[A]): Json = writer.write(value)
```
- 上記のようなJsonWriterとShape, Circleクラスがあった場合、反変の性質により、`JsonWriter[Circle]`の場所でshapeWriterが使えるようになる

##### Invariance(非変)
- `+`も`-`もつかない
- 非変の場合`F[A]`と`F[B]`はどちらもどちらの派生型ではないことを表す


```scala
sealed trait A
final case object B extends A
final case object C extends A
```
- 上記のような代数的データ型があった場合、インスタンスとしてsuper typeが使われるか、sub typeが使われるかは変位指定によって異なる

|-  |非変  |共変  |反変|
|---|---|---|---|
|super typeのインスタンスが使用される  |No  |No  |Yes |
|より詳細な型が好まれる  |No  |Yes  |No | 

