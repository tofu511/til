## 型タグ以前
- JVM言語では型はコンパイル時に消去される
- Scalaも同様に型はコンパイル時に消去される
- ランタイムの型は検証してもコンパイル時の型情報は全て入手できない

## 型タグとは
- 型タグ(`TypeTag`)はコンパイル時に入手可能な型情報を実行じに持ち込むオブジェクト
- 例えば`TypeTag[T]`はコンパイル時の型`T`のランタイム型形式をカプセル化する

## 型タグの種類
- `scala.reflect.api.TypeTags#TypeTag`
  - 全ての型情報をもつ
  - 例えば`TypeTag[List[String]]`は型`scala.List[String]`に関する全ての型情報をもつ
- `scala.reflect.ClassTag`
  - 部分的な型記述子
  - 例えば`ClassTag[List[String]]`は消去されたクラス型の情報(`scala.collection.immutable.List`)を保持する
- `scala.reflect.api.TypeTags#WeakTypeTag`
  - 抽象型の型記述子
  - 正直よくわからない
  
### typeTag
- Intを表す`TypeTag`を得る例
```scala
scala> import scala.reflect.runtime.universe._
import scala.reflect.runtime.universe._

scala> val tt = typeTag[Int]
tt: reflect.runtime.universe.TypeTag[Int] = TypeTag[Int]
```

### classTag
- Stringを表す`ClassTag`を得る例
```scala
scala> import scala.reflect._
import scala.reflect._

scala> val ct = classTag[String]
ct: scala.reflect.ClassTag[String] = java.lang.String
```


## 参考資料
- [型タグとマニフェスト](https://docs.scala-lang.org/ja/overviews/reflection/typetags-manifests.html)
