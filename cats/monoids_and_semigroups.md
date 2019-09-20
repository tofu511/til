# Monoids and Semigroups

### Integer addition
- Intの加算は"閉じた"二項演算である。つまり2つのIntは常にIntを生成する
- `a + 0 == 0 + a == a`というプロパティをもつ単位元(identity element)0がある
  - [単位元](https://ja.wikipedia.org/wiki/%E5%8D%98%E4%BD%8D%E5%85%83)
- 足す順番に関係なく同じ結果を常に得られるという、結合法則(associativity)がある
  - [結合法則](https://ja.wikipedia.org/wiki/結合法則)

```scala
(1 + 2) + 3 // 6
1 + (2 + 3) // 6
```

### Integer multiplication
- 掛け算の場合、単位元が0ではなく1になる

```scala
1 * 3 // 3
3 * 1 //3
```

- 掛け算は同じく結合法則がある

```scala
(1 * 2) * 3 // 6
1 * (2 * 3) // 6
```

### String and sequence concatenation
- 文字列連結の単位元は空文字

```scala
"" + "Hello" // Hello
"Hello" + "" // Hello
```

- 文字列連結も結合法則がある

```scala
("One" ++ "Two") ++ "Three" //OneTwoThree
"One" ++ ("Two" ++ "Three") //OneTwoThree
```

## Definition of a Monoid
- `A`のモノイドは以下の要素から成り立つ
   - `(A, A) => A`という組み合わせ(combine)操作
   - `A`の空要素

```scala
trait Monoid[A] {
  def combine(x: A, y: A): A
  def empty: A
}
```

- またモノイドのcombineとempty操作はいくつかの法則(laws)に従う必要がある
  - `combine`は結合法則(associative)に、`empty`は単位元の法則に従う

```scala
def associativeLaw[A](x: A, y: A, x: A)(implicit m: Monoid[A]): Boolean = {
  m.combine(x, combine(y, z)) == m.combine(m.combine(x, y), z)
}

def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean = {
  (m.combine(x, m.empty) == x) && (m.combine(m.empty, x) == x)
}
```

- 引き算は結合法則が成り立たないのでモノイドではない

```scala
(1 - 2) - 3 // -4
1 - (2 - 3) // 2
```

## Definition of a Semigroup
- Semigroup(半群)は、モノイドの結合(combine)部分のこと
- 多くのSemigroupはモノイドだが、Semigroupのデータ型の中には空要素(empty element)を定義できないものもある
  - 例えば、空の要素を認めないnon-emptyなSequenceや正の数が制限された場合などは、`empty`を定義できない
  - Catsの`NonEmptyList`はSemigroupの実装だが、モノイドの実装ではない

```scala
trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}
```
## Monoids in Cats
### The Monoid Type Class
- Monoidの型クラスは`cats.kernel.Monoid`で、`cats.Monoid`はそのエイリアス

### Monoid Instances
- `cats.instances`パッケージにある
- データ型によってパッケージが異なる

```scala
import cats.Monoid
import cats.instances.string._

Monoid[String].combine("Hello", " World") //Hello World
Monoid[String].empty // ""
```

- Optionを使う場合Option内のデータ型と合わせてインポートする

```scala
import cats.Monoid
import cats.instances.int._
import cats.instances.option._

Monoid[Option[Int]].combine(Option(10), Option(20)) // Some(30)
```

### Monoid Syntax
- catsはcombineメソッドのSyntax(`|+|`)を用意している
- 使うには`cats.syntax.semigroup`をインポートする

```scala
import cats.instances.string._
import cats.syntax.semigroup._

"Hello" |+| " World" |+| Monoid[String].empty // "Hello World"
```

## Applications of Monoids
### Big Data
- SparkやHadoopのようなマシンをまたがり、フォールトトレランスかつスケーラブルな分散データ分析Big Dataのアプリケーションでは、各マシンは計算結果の一部を返すので、最終的にそれらを組み合わせる(combine)必要があるが、ほとんどの場合これはモノイドと見做すことができる
  - サイトのトータルビジターの計算なら`Int`、ユニークユーザーなら`Set[User]`、99%-95%のレスポンスタイムなら`QTree`のデータ型のモノイドで計算できる
- 大規模データセットに対する分析はほとんどがモノイドであるため、このアイデアで表現力豊かで強力な分析システムを構築できる
  - Twitterの[Algebird](https://github.com/twitter/algebird)や[Summingbird](https://github.com/twitter/summingbird)はこれを行った

### Distributed Systems
- 分散システムでは異なるマシンが異なるデータの見方で終わる可能性がある
  - 例えば、あるマシンが他のマシンが受け取らなかったアップデートを受信する可能性がある
- このようなマシン間のデータ差分は、これ以上更新がなければ同じデータを保持していることになる
- これら結果整合性と呼ばれる(分散システム間で最終的にデータを同じにすること)
- 特定のデータ型は結果整合性の調整をサポートする。これらは[可換複製データ型(CRDT)](https://qiita.com/everpeace/items/bb73ec64d3e682279d26)と呼ばれる
- CRDTにおいて重要な操作は2つのインスタンスをマージする機能であり、その結果両方のインスタンスの情報がキャプチャされる
- この2つのインスタンスをマージする機能はモノイドインスタンスをもつことに依存している





