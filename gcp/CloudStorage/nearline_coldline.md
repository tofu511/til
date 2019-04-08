## ストレージクラスの概要
- google cloud storageには4つのストレージクラスがある

#### Multi-Regional Storage
- 月間可用性99.99%を超える
- SLAは99.95%
- 地域的な冗長性がある
- 世界中から頻繁にアクセスされるデータなどを保存する

#### Regional Storage
- 月間可用性99.99%を超える
- SLAは99.9%
- 地理的に近いデータを保存
- 複数の可用性ゾーンに渡って冗長
- 頻繁にアクセスするデータはそれを使用するDataProc, Compute Engineと同じリージョンに保存する

#### Nearline Storage
- 頻繁にアクセスすることが予想されないデータ(月1回程度)
- バックアップやロングテールのコンテンツに最適
- 1オペレーションあたりのコストは高め

#### Coldline Storage
- 頻繁にアクセスすることが予想されないデータ（年に1回程度）
- 障害復旧用やすぐ必要にならないデータのアーカイブなどに使う
- 1オペレーションあたりのコストは高め

## 参考資料
- https://cloud.google.com/storage/docs/storage-classes?hl=ja
