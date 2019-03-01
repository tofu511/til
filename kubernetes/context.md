## KubernetesのContextについて
- contextはざっくりいうとKubernetesに接続するための情報
- kubectlでKubertenesを操作するときの認証情報は`~/.kube/config`に書かれている
  - configには`cluster` `user` `context` の３種類を定義する
  - 上記の３種類が接続のための情報になる
  - `cluster`には接続先のクラスタ情報
  - `user`には認証情報
  - `context`には`cluster`と`user`のペアにnamespaceを指定したものを定義する
- contextを操作したいclusterのものに変えないkubectlで操作できない
- `kubectl config current-context` で現在のコンテキストを確認できる
- `kubectl config` で利用可能なコマンドが見れるので確認すると良い😊
- `kubectx` を使うと便利にcontextの変更ができる
  - https://github.com/ahmetb/kubectx
  
## 参考資料
- https://thinkit.co.jp/article/13542
