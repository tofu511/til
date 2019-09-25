# Functors

## Example of Functors
- 非公式には、Functorは`map`メソッドを持つもの
  - Option, List, Either ...
- Listのmapを例にすると、mapはリストを走査するものではなく、リスト内の全ての値を一度に変換するものだと考える必要がある
  - 適用する関数を指定すると全てのアイテムに適用されるが、リスト構造は同じままである

```scala
List(1,2,3).map(n => n + 1) // List(2,3,4)
```

- Optionに適用した場合、SomeやNoneのコンテキストは変わらない
- Eitherに適用した場合、LeftやRightのコンテキストは変わらない
- mapはコンテキスト構造を変えないため、繰り返し呼ぶことができる

```scala
List(1,2,3)
  .map(n => n + 1)
  .map(n => n * 2) // List(4,6,8)
```

- mapは繰り返しパターンではなく、データ型によらず、値の計算を順序づける方法として考える必要がある
  - データ型によって、計算時に違いはあるものの計算の順序づけとしては変わらない
    - Option
      - 値が存在する場合と存在しない場合がある
    - Either
      - 値があるかもしくはエラー
    - List
      - ゼロもしくはそれ以上の値がある

## More Example of Functors
- List, Option, Eitherのmapメソッドは関数を先行的に適用するが、計算の順序づけるアイデアはこれよりももっと一般的である

### Futures
- Futureは非同期計算をキューイングし、先行する計算が完了すると非同期計算を適用する順序づけられた非同期計算のファンクターのこと
- Futureのmapの動作はList, Option, Eitherと異なる
- Futureは内部の状態について保証をしない
- Futureでの計算は進行中(Ongoing)、完了(Complete)、拒否(Rejected)のいずれかで表現される
- Futureが完了した場合、mapの関数がすぐに呼ばれるが、完了以外の場合、関数はスレッドプールのキューに入り、あとで呼び出す
- Futureでは関数がいつ呼び出されるかは分からないが、呼び出しの順序はわかる

- (ちなみに)ScalaのFutureは参照透過的ではないので、純粋な関数型プログラミングの例としてはよくない
  - Futureは常に結果をキャッシュするが、それらを調整するすべがないため、副作用のある計算をラップすると予期せぬ結果を取得する場合がある
  - 詳しくは[こちら](https://www.reddit.com/r/scala/comments/3zofjl/why_is_future_totally_unusable/)の例を参照してください
  
### Functions(?!)
- 単一引数の関数も実はファンクター
  - ただし型を微調整する必要がある
- `A => B`という関数は2つの型パラメータをとる
  - `A`型を受け取って`B`型を返す
- `A => B`を実現するためにはパラメータの型を修正し、結果の型を変える必要がある
  - `X => A`から始まり
  - `A => B`の関数を提供して
  - `X => B`を得る

```scala
import cats.instances.function._
import cats.syntax.functor._

val func1: Int => Double = (x: Int) => x.toDouble // func1: Int => Double
val func2: Double => Double = (y: Double) => y * 2 // func2: Double => Double

func1 map func2 // Int => Double //Int => Double にmapで Double => Doubleの関数を適用すると Int => Doubleの関数を合成できる 
(func1 map func2)(1) // 2.0

(func1 andThen func2)(1) // 2.0
```

- (つまり)Function1のmappingは関数の合成である
- だがしかし、これが一般的な順序づけ計算とどんな関係があるのか?
- 関数の合成は順序づけであると考えてみてはどうだろうか?
- mapを呼び出しても実際にはどの操作も実行されないが、引数を最後の関数に渡すことで全ての操作が順番に実行される

```scala
val func = 
  ((x :Int) => x.toDouble)
  .map(x => x + 1)
  .map(x => x * 2)
  .map(x => x + "!") // Int => String
  // この時点では操作は順番に実行されない


func(123) // "248.0!"
// 引数が与えられた時点で操作が順番に実行される
```
  
## Definition of a Functor
- Functorとは順序づけの計算をカプセル化したもの。正式には `F[A]`型に`(A => B) => F[B]`を行う`map`がついたもの
- Catsでの定義は以下の通り

```scala
package cats

import scala.language.higherKinds

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}
```

- `F[_]`というシンタックスはこの後の型コンストラクタと高カインド型で扱う
- Functorでは操作を1つずつ呼んでも、1つの関数として合成しても、同じセマンティックスを保証するために守らないといけない法則が2つある
  - Indentity
    - mapをidentity function(恒等写像: 同じ値を常にそのまま返すようなmapのこと)で呼んだときは何もしない
    - `fa.map(a => a) == fa`
  - Composition
    - 関数f,gのmappingはfの後にgをmappingするのと同じになる
    - `fa.map(g(f(_))) == fa.map(f).map(g)`


## Aside: Higher Kinds and Type Constructors
- カインドは型のための型のようなもので、型にある「穴」の数を表す
- Listは「穴」が1つの型コンストラクタ
  - 「穴」に型を入れることで`List[Int]`や`List[A]`を作っている
- ややこしいが、`List`は型コンストラクタで`List[A]`は型である
- Scalaでは、型コンストラクタにアンダースコアを使う

```scala
class MyClass[F[_]] {}
```

## The Functor Type Class
- Catsでは`cats.Functor`をインポートする
- Functorは`A => B`の関数を`F[A] => F[B]`に変換する`lift`メソッドも提供する

```scala
val list = List(1,2,3)
Functor[List].map(list)(_ * 2) // List(2, 4, 6)

val func = (x: Int) => x + 1
val func2 = Functor[Option].lift(func) // Option[Int] => Option[Int]
func2(Option(2)) // Some(3)
```

