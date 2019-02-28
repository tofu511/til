## Dockerのmysql imageでの初期化方法
`/docker-entrypoint.initdb.d/`というディレクトリに初期化用のSQLやスクリプトをおくことで、Image起動時に実行される。\
`mysql/init`ディレクトリに初期化用のSQLやスクリプトを置き、volumeで`/docker-entrypoint.initdb.d/`に渡すとよい。

イメージ
```
version: "3"
services:
  db:
    image: "mysql:5.7"
    ports:
      - "4306:3306"
    volumes:
      - "./mysql/init:/docker-entrypoint-initdb.d" # こんな感じ
```

## Dockerコンテナを立ち上げた後にコンテナのmysqlに接続する方法
- port 4306から接続できるとしたら以下の感じで接続できる\
`mysql -P 4306 -h 127.0.0.1 -u root -p`
