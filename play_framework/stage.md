 ## stageコマンドを使って`target/universal`配下に実行ファイルを生成する
 - play frameworkで`sbt stage`を実行することで`target/universal`配下に実行ファイルを生成することができる
 - `sbt universal:packageBin`を実行すると`universal`のzipファイルを生成する
  - 上記コマンドは`sbt-native-packager`を入れないと実行できないかもしれない
 - `bin`配下にバイナリが生成されるのでバイナリを実行することでアプリを動かせる(はず)
 
 ## 参考資料
 - [sbt-native-packager Universal Plugin](https://sbt-native-packager.readthedocs.io/en/v1.3.14/formats/universal.html)
