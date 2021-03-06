# 1章 オブジェクト指向パラダイム

## メモ
- オブジェクト指向は構造化プログラミングの欠点を補完するために生まれた
- 要求は常に変化するので対応するしないといけない
- Martin Fowlerによるとソフトウェア開発のプロセスには以下の3つの観点がある
    1. 概念(conceptual)
        - 何に対して責任があるか？
    1. 仕様(specification)
        - どのように使用されるか？
    1. 実装(implementation)
        - どのようにして自身の責任をまっとうするか？

### オブジェクト指向パラダイム以前
- 機能分解という問題を小さな機能に分割することで、構成する機能の洗い出しを行っていた
- 機能分解では分解した機能を適切な順序で呼び出すmain関数が必要になる
    - 複雑化しやすい
    - 将来に変更に対応しにくい
- main関数が順序を正しく制御するのではなく、部分機能に振る舞いの責任を**委譲(delegation)**するともっと楽になる

### オブジェクト指向パラダイム
- オブジェクトを定義する利点はオブジェクト自体に責任を持たせてものごとを定義できる点
- Martin Fowlerの観点では
    - 概念レベルにおいて、オブジェクトは責任の集合である
    - 仕様レベルにおいて、オブジェクトはその他のオブジェクトや自らを起動することができるメソッド（振る舞い）の集合となる
    - 実装レベルにおいて、オブジェクトはコードとデータ、そしてそれら相互の演算処理となる
- オブジェクト指向を概念レベルや仕様レベルから考察することで、オブジェクト指向の力を引き出すことができる
