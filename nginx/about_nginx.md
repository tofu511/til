## nginxとは
- Webサーバ兼リバースプロキシとして使えるソフトウェア
- [C10K問題](https://yohei-a.hatenablog.jp/entry/20120504/1336157667)に対応したWebサーバである
- Webサーバとして静的なコンテンツを提供できるが動的なコンテンツはnginx単体では提供できないのでWebアプリケーションサーバと連携する必要がある
- WebアプリケーションをFastCGIなどに対応したアプリケーションサーバ上で動作させ、ネットワークもしくはUNIXドメインソケットを経由して利用する
- キャッシュ、ロードバランサなどの機能も持っている

## 参考資料
- [nginx連載1回目: nginxの紹介](https://heartbeats.jp/hbblog/2012/01/nginx01.html)
- [C10K問題とは](https://yohei-a.hatenablog.jp/entry/20120504/1336157667)
