# 低レベルアクセスへの入口2: io.Reader

## io.Reader
- io.Writerのようにプログラムで外部からデータを読み込むための機能もGoではインターフェースとして抽象化されている
```go
type Reader interface {
  func Read(p []byte) (n int, err error)
}
```
- 引数のpは読み込んだ内容を一時的に入れておくバッファ
- Goでメモリを確保するにはmake()を使うと良い
```go
// 1024バイトのバッファをmakeで作る
buffer := make([]byte, 1024)

// sizeは実際に読み込んだバイト数、errはエラー
size, err := r.Read(buffer)
```

## io.Readerの補助関数
### 読み込みの補助関数
- ioutil.ReadAll()は終端記号に当たるまで全てのデータを読み込んで返す
  - メモリに収まらない場合は使えない
- io.ReadFull()は決まったバイト数だけ確実に読み込む
```go
// 4バイト読めないとエラー
buffer := make([]byte, 4)
size, err := io.ReadFull(reader, buffer)
```

### コピーの補助関数
- io.Copy()は全て読み尽くして書き込む
```go
// 全てコピー
writeSize, err := io.Copy(writer, reader)
// 指定したサイズだけコピー
writeSize, err := io.CopyN(writer, reader, size)
```

## io.Readerを満たす構造体でよく使うもの
### 標準入力
- os.Stdinは標準入力に対応するオブジェクト
```go
package main

import (
	"fmt"
	"io"
	"os"
)

func main() {
	for {
		buffer := make([]byte, 5)
    // 5バイトずつ読み込む
		size, err := os.Stdin.Read(buffer)
		if err == io.EOF {
			fmt.Println("EOF")
			break
		}
		fmt.Printf("size=%d input='%s'\n", size, string(buffer))
	}
}
```

### ファイル入力
- ファイルは一度開いたらClose()する必要がある
  - deferを使うと良い
```go
package main

import (
	"io"
	"os"
)

func main() {
	file, err := os.Open("README.md")
	if err != nil {
		panic(err)
	}
	defer file.Close()
	io.Copy(os.Stdout, file)
}
```
