## Dockerのmysql imageでの初期化方法
`/docker-entrypoint.initdb.d/`というディレクトリに初期化用のSQLやスクリプトをおくことで、Image起動時に実行される。\
`mysql/init`ディレクトリに初期化用のSQLやスクリプトを置き、volumeで`/docker-entrypoint.initdb.d/`に渡すとよい。

イメージ
```yaml
version: "3"
services:
  db:
    image: "mysql:5.7"
    ports:
      - "4306:3306"
    volumes:
      - "./mysql/init:/docker-entrypoint-initdb.d" # こんな感じ
```

### 初期化時の注意点
- docker-entrypoint-initdb.d内に配置したsqlでDBの作成とユーザーへの権限の付与を行おうとする場合、権限の付与がうまくいかないことがある。その場合は`MYSQL_DATABASE` `MYSQL_USER` `MYSQL_PASSWORD`でユーザーとデータベースを作成するとユーザーに権限が付与される

```bash
if [ "$MYSQL_USER" -a "$MYSQL_PASSWORD" ]; then
	echo "CREATE USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD' ;" | "${mysql[@]}"

	if [ "$MYSQL_DATABASE" ]; then
		echo "GRANT ALL ON \`$MYSQL_DATABASE\`.* TO '$MYSQL_USER'@'%' ;" | "${mysql[@]}"
	fi

	echo 'FLUSH PRIVILEGES ;' | "${mysql[@]}"
fi
```


## Dockerコンテナを立ち上げた後にコンテナのmysqlに接続する方法
- port 4306から接続できるとしたら以下の感じで接続できる\
`mysql -P 4306 -h 127.0.0.1 -u root -p`

## 参考資料
- [Docker上でMySQLを利用する際のセットアップについて](https://blog.mosuke.tech/entry/2018/04/21/basic-mysql-on-docker/)
- [DockerオフィシャルのMySQL（MariaDB）コンテナの挙動をDockerfile周辺から読み解く](https://qiita.com/kazuyoshikakihara/items/f0c5158c700bb7a5df9f)
- https://github.com/docker-library/mariadb/blob/master/docker-entrypoint.sh
