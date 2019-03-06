## Dockerのネットワーク以前
- そもそもウェブ・アプリケーションの構築は、安全についての考慮が必要
- 安全にするためにDockerネットワークを使う
- ネットワークとは、コンテナのために完全な分離(isolation)を提供するもの

## Dockerのネットワークについて
- Dockerにはデフォルトで3つのネットワークがある
  - bridge
  - host
  - none
- 現在のネットワークは`Docker network ls`で確認できる
- `Docker network inspect [network名]`でネットワークの詳細を確認できる

## 参考資料
- [Docker入門（第五回）〜コンテナ間通信〜](https://knowledge.sakura.ad.jp/16082/)
- [Docker コンテナ・ネットワークの理解](http://docs.docker.jp/engine/userguide/networking/dockernetworks.html)
