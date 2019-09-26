# Monads
- モナドはScalaでもっとも一般的な抽象化の１つ
- ざっくり言うと、モナドはコンストラクタと`flatMap`メソッドを持つもの
- 前の章でみたFunctors(Option, List, Future)も同じくモナド

## What is Monad?
- モナドは計算を順序付けるためのメカニズムである
  - ただこれだとFunctorの説明と同じなのでもう少し説明が必要
- 前の章でFunctorは複雑さを取り除いた計算の順序づけであることを説明した
- しかしFunctorはこの複雑さを順序づけの最初に発生させるという点で制限がある
- モナドのflatMapを使うと中間の複雑さを考慮して次に何が起きるかを指定できる
- 例をみていくと・・・

### Options
- 以下のようなコードがあったとする

```scala
import scala.util.Try

def parseInt(str: String): Option[Int] = Try(str.toInt).toOption

def divide(a: Int, b: Int): Option[Int] = if (b == 0) None else Some(a / b)
```

```scala
parseInt("1") // Some(1)
parseInt("a") // None

divide(0, 0) // None
divide(2, 1) // Some(2)
```

- `parseInt`と`divide`は失敗した時に`None`を返す
- flatMapはこの失敗した時(Noneを返すとき)を計算する時に無視する

```scala
def stringDivideBy(aStr: String, bStr: String): Option[Int] = 
  parseInt(aStr).flatMap{ aNum => 
    parseInt(bStr).flatMap { bNum 
      => divide(aNum, bNum) 
    } 
  }
```

```scala
stringDivideBy("12", "2") // Some(6)
stringDivideBy("12", "a") // None
stringDivideBy("1", "0") // None
```

- この`stringDivideBy`メソッドの中身をみていくと
  - 最初の`parseInt`はNoneかSomeを返す
  - もしSomeが返ってきたら、flatMapは関数を呼び、aNumを渡す
  - 2番目の`parseInt`もNoneかSomeを返す
  - もしSomeが返ってきたら、flatMapは関数を呼び、bNumを渡す
  - divide関数が結果としてSomeかNoneを返す
- 各ステップでflatMapは関数を呼び出すか選択し、関数が次の計算を生成する
- flatMapはOptionを返すので再度flatMapを呼ぶことで、順序づけの計算を行いながら、fail-fastなエラーハンドリングを実現できる
- 全てのモナドはFunctorでもあるため、flatMapとmapの両方を使うことができる
- またflatMapとmapがあるとfor内包表記が可能になり、順序づけの振る舞いを明確にすることができる

```scala
def stringDivideBy(aStr: String, bStr: String): Option[Int] =
  for {
    aNum <- parseInt(aStr)
    bNum <- parseInt(bStr)
    ans <- divide(aNum, bNum)
  } yield ans
```
