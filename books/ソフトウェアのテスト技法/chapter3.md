# 同値クラステスト

## 概要
- 同値クラステストは十分なテストカバレッジを保ちながらテストケースの数を管理可能な程度に減らすための技法の一つ
- 同値クラスはモジュールで同等に処理されるデータ、あるいは同じ結果を返すデータの集合を意味する
    - 同値クラス内にあるテストケースで欠陥が検出された場合には、同じ同値クラスの他の全てのテストケースで同じ欠陥が検出される
    - 同値クラス内のテストケースで欠陥が検出されない場合には、同じ同値クラスの他のどのようなテストケースでも欠陥は検出されない
- 異常な値に関しては契約による設計か防御的設計のどちらで実装されているかによって取りうるアプローチが変わる
    - 契約による設計で事前処理・事後処理が正常になされるなら、異常な値に関するテストは不要かもしれない
    - 契約による設計を行なっていない場合、防御的に正常な値のテストと異常な値のテストをやっても良い

## 技法の解説
- 入力タイプごとに同値テストを作る（必要になる）
- 入力が連続する範囲の場合、有効値によるクラスと有効値の上下で無効値クラスがある
    - 100 ~ 300を有効とする場合、無効値は n < 100, n > 300の2つがある
- 複数値から選択する同値テストの場合、バグが出た場合のリスク次第だが、すべてのパターンを網羅するのは時間的に厳しいので、複数の入力項目を同時にテストするテストケースを作成する
    - 全てが有効なパターンのテストケースを作成する
    - 無効値のテストをする場合、無効値の項目を一つずつ変えてテストする
    - 無効値を複数組み合わせると何が原因で無効となるかが分からなくなる
- 同値テストが最も適しているのは入力データの大半がある範囲内、または集合内の値を取るようなシステム
    - 同値クラス内のデータを同じ方法でシステムが処理していることが前提になる
