# システムコール
## システムコールとは何か？
- システムコールとは「特権モードでOSの機能を呼ぶ」こと

### CPUの動作モード
- コンピュータシステムではプログラムの実行単位のことをプロセスと呼ぶ
- 通常はOSがプロセスを管理している
- CPUではOSが全てのプロセス管理を行えるような仕組みが用意されている
  - 仕組みの１つとして動作モードがある
    - 特権モード
    - ユーザーモード
- 特権モードではCPUの機能が基本的に全て使える
  - 配下の全てのプロセスのために資源管理や場合によっては資源を取り上げることができる
- ユーザーモードでは上記のような機能はCPUレベルで使用できないようになっている

### システムコールでモードの壁を越える
- 多くのOSではシステムコールを介して、特権モードでのみ許されている機能をユーザーモードのアプリケーションから利用できるようにしている

### システムコールがないとどうなるか?
- プロセス間通信ができないので、計算結果を画面などに出力することができない
- ファイル書き込みができないので、計算結果を保存することができない
- 計算結果を共有メモリに書き出すことができない
- 外部のWebサービスなどと通信ができない

## Go言語におけるシステムコールの実装
- syscallパッケージに定義された関数を呼び出す

## POSIXとC言語の標準規格
- POSIX(Portable Operation System Interface)は、OS間で共通のシステムコールを決めることで、アプリケーションの移植性を高めるために作られたIEEE規格のこと
