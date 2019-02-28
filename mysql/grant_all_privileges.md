## GRANT ALL PRIVILEGESについて
- [公式ドキュメント](https://dev.mysql.com/doc/refman/5.6/ja/grant.html)
- GRANTステートメントで権限を付与する権限をユーザーに与える
- ユーザーを作成することもできる\
`GRANT ALL PRIVILEGES ON *.* TO 'ユーザー名'@'localhost' IDENTIFIED BY 'パスワード';`
- 通常は`CREATE USER`でアカウントを使用してから`GRANT`で権限を付与する
- GRANTステートメントで指定されたアカウントがまだ存在しない場合は、指定された条件の下でアカウントが作成される
>ただし、GRANT ステートメントで指定されたアカウントがまだ存在しない場合、GRANT は、あとで NO_AUTO_CREATE_USER SQL モードの説明に示されている条件の下でそのアカウントを作成する可能性があります。
