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



