## CloudWatch Logsとは
- EC2、CloudTrail、Route53やその他ソースのログファイルの監視、保存、アクセスができるサービス

## CloudWatch Logsの概念
### ログイベント
- モニタリングしているアプリやリソースによって記録されたアクティビティのレコードのこと
- プロパティは２つ
  - イベント発生時のタイムスタンプ
  - 生のイベントメッセージ

### ログストリーム
- 同じソースを共有する一連のログイベントのこと
- モニタリングしているアプリやリソースから送信された順序でイベントを表すものである

### ロググループ
- 保持、監視、アクセス制御について同じ設定を共有するログストリームのグループのこと
- 各ログストリームは１つのロググループに属している必要がある

### メトリクスフィルタ
- 取り込まれたイベントからメトリクスの監視データを抽出して、CloudWatchメトリクスのデータポイントに変換するフィルタ
- メトリクスフィルタはロググループに割り当てられ、ロググループに割り当てられたフィルタはログストリームに適用される

### 保持設定
- CloudWatch Logsにログイベントを保持する期間を指定するために使う
- ロググループに割り当てられ、ロググループに割り当てられた保持期間はログストリームに適用される

## 参考資料
- [Amazon CloudWatch Logs とは](https://docs.aws.amazon.com/ja_jp/AmazonCloudWatch/latest/logs/WhatIsCloudWatchLogs.html)