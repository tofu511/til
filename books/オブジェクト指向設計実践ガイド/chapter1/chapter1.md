# 1章 オブジェクト指向設計

## 1.1 設計の賞賛
### 設計が解決する問題
- アプリケーションが永久に変化しないなら設計は気にする必要がない
- 変更の必要性ことが設計を重要にする

### 変更が困難な理由
- 正しいメッセージを正しいオブジェクトへ届けるにはメッセージの送り手が受け手のことを知っている必要がある
  - この知識が2つのオブジェクト間に依存関係を作り出す
  - この依存関係が変更の妨げになる
- オブジェクト指向設計とは **依存関係を管理すること**
- アプリケーションが小さければ全てを頭にとどめておけるが、大きなアプリケーションでは無理

### 設計の実用的な定義
- コードの構成こそが「設計」である
- 実用的な設計とは未来を推測するものではなく、未来を受け入れるための選択肢を保護するもの
  - 選択するのではなく、動くための余地を設計者に残しておくもの
- 設計の目的は「後にでも」設計をできるようにすることで、その第一の目標は変更コストの削減である
