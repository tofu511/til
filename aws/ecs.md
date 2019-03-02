## ECSの基本

### ECSとは
- ECS(Amazon Elastic Container Service)はコンテナ化アプリを動かす環境のこと

### 基本用語
- Cluster
  - Taskを配置できるコンテナインスタンスの論理的なグループ
- Service
  - 特定数のTaskを同時に実行、管理するサービス
- Task
  - Task Definitionをインスタンス化したもの
  - プログラミング言語でいうと
- Task Definition
  - コンテナの起動方法などを指定したもの
    - リビジョン
    - コンテナの定義
    - Volume
    - などを指定する
  - Docker Composeのcompose.ymlとだいたい同じ
  - プログラミング言語でいうとTask DefinitionがClassみたいなものでTaskがInstanceみたいなもの

## 参考資料
- [Amazon EC2 Container Service(ECS)の概念整理](https://qiita.com/NewGyu/items/9597ed2eda763bd504d7)
- [AWSの用語集](https://docs.aws.amazon.com/ja_jp/AmazonECS/latest/developerguide/glossary.html)
