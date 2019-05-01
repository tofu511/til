## Bigtableの概要

### ストレージモデル
- テーブルは行と列で構成される
- 各行は単一の行キーでインデックスに登録される
- 関連する列は通常、列ファミリーとしてグループ化される
- 各列は列ファミリーと列修飾子(特定の列ファミリー内で一意の名前)の組み合わせによって識別される
- 各行と列の交差点には、異なるタイムスタンプで複数のセルを格納できるので、データの変更履歴を残すことができる
- Bigtableではデータが格納されていない領域は確保(消費)されない


### アーキテクチャ
- クラスタ内の各ノードがクラスタに対するリクエストの一部を処理する
- クラスタ内のマシン台数に正比例してスケールする
- データはノード自体に格納されるのではなく、各ノードは連続する行ブロック(タブレット)のセットに対するポインタを保持している
- ポイントを持っているだけなので、タブレットの移動が高速に行える
	- 実データをコピーしないため
- ノードの障害があってもデータは失われない
- ノードの復旧が高速に行える
	- 新しいノードにメタデータを移動すれば良いので


## 参考資料
- https://cloud.google.com/bigtable/docs/overview