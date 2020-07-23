# Facadeパターン

## Facadeパターンとは
- サブシステム内に存在している複数のインターフェース群に対する、統一したインターフェースを提供する。Facadeは高レベルのインターフェースを定義することにより、サブシステムを容易に扱えるようにするものである

|    |    |
| ---- | ---- |
| 目的 | 既存システムの使用方法を簡素化したい。独自のインターフェースを定義する必要がある|
| 問題| 複雑なシステムの一部だけを使用する必要がある。あるいは、特定の方法でシステムとのやりとりが必要|
| 解決策 | Facadeによって、既存システムを使用するクライアント向けに新たなインターフェースを作成する|
|構成要素と協調要素| このパターンは、複雑なシステムの使用が容易になるよう、クライアントに対して簡潔なインターフェースを提供する |

---
- Before
```mermaid
classDiagram
    ClientA --> Database
    ClientB --> Database
    ClientA --> Model
    ClientB --> Model
    ClientA --> Element
    ClientB --> Element
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG4gICAgQ2xpZW50QSAtLT4gRGF0YWJhc2VcbiAgICBDbGllbnRCIC0tPiBEYXRhYmFzZVxuICAgIENsaWVudEEgLS0-IE1vZGVsXG4gICAgQ2xpZW50QiAtLT4gTW9kZWxcbiAgICBDbGllbnRBIC0tPiBFbGVtZW50XG4gICAgQ2xpZW50QiAtLT4gRWxlbWVuIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)

- After
```mermaid
classDiagram
    ClientA --> DatabaseFacade
    ClientB --> DatabaseFacade
    DatabaseFacade --> Database
    DatabaseFacade --> Model
    DatabaseFacade --> Element
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG4gICAgQ2xpZW50QSAtLT4gRGF0YWJhc2VGYWNhZGVcbiAgICBDbGllbnRCIC0tPiBEYXRhYmFzZUZhY2FkZVxuICAgIERhdGFiYXNlRmFjYWRlIC0tPiBEYXRhYmFzZVxuICAgIERhdGFiYXNlRmFjYWRlIC0tPiBNb2RlbFxuICAgIERhdGFiYXNlRmFjYWRlIC0tPiBFbGVtZW50IiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)

- その他の用途
    - システムアクセスをFacade経由にして利用状態を監視する
    - システムを交換する際に、Facadeのprivateメンバだけなら、影響を最小限に抑えられる