# 低レベルアクセスへの入口1:io.Writer
- Goが少ない記述量でシステムプログラミングを行えるのは低レイヤーを抽象化により扱いやすくしているため
  - io.Writer: 出力の抽象化
  - io.Reader: 入力の抽象化
  - channel: 通知の抽象化

##  io.WriterはOSが持つファイルのシステムコールの相似形
- OSでは`syscall.Write()`のようなシステムコールを**ファイルディスクリプタ**というものに対して呼ぶ
- ファイルディスクリプタは一種の識別子（数値）で、この数値を指定してシステムコールを呼ぶと数値に対応するモノにアクセスできる
- ファイルディスクリプタに対応するのはファイルだけではなく、標準入出力、ソケット、OSやCPUに内蔵されている乱数生成の仕組みなど、ファイルでないものにも割り当てられ、ファイルと同じようにアクセスできる

## Go言語のインターフェース
- Go言語のインターフェースは構造体などの具象型が満たすべき仕様(持つべきメソッド)を表現するための言語仕様
- インターフェースに宣言されている全てのメソッドがデータ型に対して定義されている場合、「そのデータ型はインターフェースを満たす」と表現する
```go
package main

import (
    "fmt"
)

type Talker interface {
  Talk()
}

type Greeter struct {
  name string
}

func (g Greeter) Talk() { 
  fmt.Println("Hello, my name is %s\n", g.name)
}

func main() {
  var talker Talker
  talker = &Greeter{"Mike"}
  talker.Talk()
}

```

## io.Writerは「インターフェース」
- os/file.goのWriteメソッド

```go
func (f *File) Write(p []byte) (n int, err error) {
  if f == nil {
    return 0, ErrInvalid
  }
  n, e := f.write(b)
  :
}
```

- io.WriterはWrite()メソッドの仕様が宣言されているインターフェース

```go
type Writer interface {
  Write (p []byte) (n int, err error)
}
```
- *Fileはio.Writerインターフェースを満たしていると言える

## io.Writerを使う構造体の例

### ファイル出力
```go
package main

import (
    "os"
)

func main() {
  file, err := os.Create("test.txt")
  if err != nil {
    panic(err)
  }
  file.Write([]byte("os.File example\n")) // Write()が受け取るのはバイト列なので変換している
  file.Close()
}
```

### 画面出力
```go
package main

import (
    "os"
)

func main() {
  os.Stdout.Write([]byte("os.Stdout example\n"))
}
```

### バッファ
- `bytes.Buffer`はWrite()で書き込まれた内容を貯めておいてあとでまとめて結果を受け取れる

```go
package main

import (
    "bytes"
    "fmt"
)

func main() {
  var buffer bytes.Buffer
  buffer.Write([]bytes("bytes.Buffer example\n"))
  fmt.Println(buffer.String())
}
```
- strings.Builderは書き出し専用のbytes.Buffer
```go
package main

import (
    "strings"
    "fmt"
)

func main() {
  var builder strings.Builder
  builder.Write([]bytes("strings.Builder example\n"))
  fmt.Println(builder.String())
}
```

### インターネットアクセス通信
```go
package main

import (
	"io"
	"net"
	"os"
)

func main()  {
	conn, err := net.Dial("tcp", "ascii.jp:80")
	if err != nil {
		panic(err)
	}
	io.WriteString(conn, "GET / HTTP/1.0\r\nHost: ascii.jp\r\n\r\n")
	io.Copy(os.Stdout, conn)
}
```
- 上記のコードは以下のようなレスポンスを返す
```
HTTP/1.1 302 Found
Date: Wed, 27 Mar 2019 14:27:03 GMT
Server: Apache
Location: https://ascii.jp/
Content-Length: 259
Connection: close
Content-Type: text/html; charset=iso-8859-1

<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
<html><head>
<title>302 Found</title>
</head><body>
<h1>Found</h1>
<p>The document has moved <a href="https://ascii.jp/">here</a>.</p>
<hr>
<address>Apache Server at ascii.jp Port 80</address>
</body></html>
```

### io.Writerのデコレータ(オブジェクトをラップして追加の機能を実現するもの)
- `io.MultiWriter()` は複数のio.Writerを受け取り、それら全てに対して、書き込まれた内容を同時に書き込むデコレータ
```go
package main

import (
	"io"
	"os"
)

func main()  {
	file, err := os.Create("test.txt")
	if err != nil {
		panic(err)
	}
	writer := io.MultiWriter(file, os.Stdout)
	io.WriteString(writer, "io.MultiWriter example\n")
}
```

### フォーマット
- Jsonをフォーマットする
```go
package main

import (
	"encoding/json"
	"os"
)

func main()  {
	encoder := json.NewEncoder(os.Stdout)
	encoder.SetIndent("", "	")
	encoder.Encode(map[string]string{
		"example": "encoding/json",
		"hello": "world",
	})
}
```

## インターフェースの実装状況・利用状況を調べる
- GoではJavaのように構造体側に宣言を行わない
- Goでは構造体がインターフェースを満たすメソッドを持っているかどうかはインターフェースの変数に構造体のポインタを代入したり、メソッドの引数にポインタを渡したりするときに自動的に確認される
- `godoc`コマンドで起動時に`-analysis type` をつけるとインターフェースの分析を行ってくれる
	- `godoc -http ":6060" -analysis type`
[![Image from Gyazo](https://i.gyazo.com/544f3e4c5666af50a5c373f9d12192a9.png)](https://gyazo.com/544f3e4c5666af50a5c373f9d12192a9)

## 柔軟性が高く、パフォーマンスの良い設計のためのTips
- ファイルを読み書きするコードを作成する場合でも、なるべくio.Writerやio.Readerを関数の引数に扱うのが望ましい
	- ファイル名で受け取る関数を作ってしまうとネットワーク経由やオンメモリの内容を直接読み書きできなくなってしまう
