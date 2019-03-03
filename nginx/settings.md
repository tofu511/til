## nginxの設定について
- nginxの設定ファイルは通常`/etc/nginx`になる
- `/etc/nginx`にnginx.confを配置する
- includeを使用することで他のファイルから設定を読み込むことができる
- conf.dというディレクトリの.confファイルをincludeするというやり方をよく行う
  - `include /etc/nginx/conf.d/*.conf;`
- nginxの設定はディレクティブ(命令)ごとに決められた場所に記載する必要がある。
  - core: プロセスの制御や設定ファイルやエラーログに関するモジュール
  - event: イベント処理に関するモジュール
  - http: httpに関するモジュール
  - mail: mailに関するモジュール
  ```nginx
  coreモジュールの設定

  events {
      eventモジュールの設定
  }

  http {
      httpモジュールの設定
  }

  mail {
      mailモジュールの設定
  }
  ```

### バーチャルサーバの設定
- バーチャルサーバとはApache HTTP Serverでのバーチャルホストのこと
- conf.dディレクトリにバーチャルサーバごとの設定ファイルをおくことでその設定ファイルが読み込まれる

```nginx
# サーバディレクティブにはバーチャルサーバの設定を記述する
server {

    listen       80; # バーチャルサーバがリクエストを受け付けるIPアドレス、ポート番号、UNIXドメインソケットを設定する
  # listen       192.0.2.1:80; # IPアドレスを指定する場合
  # listen       unix:/var/run/nginx.sock; # UNIXドメインソケットを指定する場合
  
    server_name  localhost; # nginxはHTTPリクエストのHostと一致するバーチャルサーバの設定を返す
  # server_name example.com www.example.com; # サーバ名を指定する場合
    
    # locationディレクティブではURIのパスごとの設定を記載する
    # リクエストURIのパスがlocationディレクティブのパスの条件に一致した場合にlocationコンテキストの設定が適用される
    location / { 
        root   /usr/share/nginx/html; # rootディレクティブではドキュメントルートのディレクトリを設定する
        index  index.html index.htm;
    }

    # error_pageディレクティブには指定したエラーコードが発生したときに表示するページのURIを指定する
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

    location ~ /\.ht {
        deny  all;
    }
}
```

## 参考資料
- [nginx連載3回目: nginxの設定、その1](https://heartbeats.jp/hbblog/2012/02/nginx03.html)
- [nginx連載4回目: nginxの設定、その2](https://heartbeats.jp/hbblog/2012/04/nginx04.html)
- [nginx連載5回目: nginxの設定、その3](https://heartbeats.jp/hbblog/2012/04/nginx05.html)

