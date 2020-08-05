# Observerパターン

## Observerパターンとは

|    |    |
| ---- | ---- |
| 目的 | オブジェクト間に1対多の依存関係を定義し、あるオブジェクトの状態が変化した際、それに依存するすベてのオブジェクトに対して自動的に通知、更新が行われるようにする |
| 問題| 変化する一連のオブジェクトに対して、あるイベントが発生したことを通知する必要がある
| 解決策 | オブザーバは観察対象がイベントの監視を一括して行えるよう、その監視義務を委譲する
| 構成要素と協調要素 | オブザーバは自らを観察対象に登録するため、観察対象はオブザーバの存在を知っている。特定のイベントが発生した際、観察対象は全てのオブザーバに対して通知を行う必要がある。オブザーバの責務は観察対象に対する自らの登録と、通知の際における観察対象からの情報取得である
| 因果関係 | オブザーバがイベントの一部にしか興味を持っていない場合でも、観察対象はオブザーバに対して通知を行う可能性がある。観察対象がオブザーバに通知したあと、観察対象の持つ何らかの追加情報が必要になった場合、別途やりとりが発生することもある
| 実装 | イベント発生時の通知先イベント(Observer)を用意し、イベントの発生を監視している、あるいはイベント自体を発生させるオブジェクト(Subject)に自らを登録させる。イベント発生時、SubjectはObserverにイベントの発生を通知する。オブザーバとなる全てのオブジェクトにObserverインターフェースを実装するため、Adapterパターンが使用されることもある

```mermaid
 classDiagram
  Observer <|-- ConcreteObserver
  Subject <|-- ConcreteSubject
  Subject o-- Observer
  
  class Observer {
    Update()
  }
	class Subject {
    Attach(in Observer)
    Detach(in Observer)
    Notify()
  }

  class ConcreteObserver {
    observerState
    Update()
  }

  class ConcreteSubject {
    subjectState
    GetState()
    SetState()
  }  
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG4gIE9ic2VydmVyIDx8LS0gQ29uY3JldGVPYnNlcnZlclxuICBTdWJqZWN0IDx8LS0gQ29uY3JldGVTdWJqZWN0XG4gIFN1YmplY3Qgby0tIE9ic2VydmVyXG4gIFxuICBjbGFzcyBPYnNlcnZlciB7XG4gICAgVXBkYXRlKClcbiAgfVxuXHRjbGFzcyBTdWJqZWN0IHtcbiAgICBBdHRhY2goaW4gT2JzZXJ2ZXIpXG4gICAgRGV0YWNoKGluIE9ic2VydmVyKVxuICAgIE5vdGlmeSgpXG4gIH1cblxuICBjbGFzcyBDb25jcmV0ZU9ic2VydmVyIHtcbiAgICBvYnNlcnZlclN0YXRlXG4gICAgVXBkYXRlKClcbiAgfVxuXG4gIGNsYXNzIENvbmNyZXRlU3ViamVjdCB7XG4gICAgc3ViamVjdFN0YXRlXG4gICAgR2V0U3RhdGUoKVxuICAgIFNldFN0YXRlKClcbiAgfSAiLCJtZXJtYWlkIjp7InRoZW1lIjoiZm9yZXN0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)

### 例
#### 国際eコマースで考える
- 新規登録時に以下のことを行う
    - 新規顧客宛に挨拶メールを送る
    - 顧客の住所を郵便局からのデータを用いて確認する

```mermaid
classDiagram
  WelcomeLetter <.. Customer
  AddrVerification <.. Customer
  Observer <|-- WelcomeLetter
  Observer <|-- AddrVerification

  class Customer {
    +attach()
    +detach()
    +notify()
    +getState()
    +setState()
  }

  class Observer {
    +update(in: Customer)
  }

  class WelcomeLetter
  class AddrVerification
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG4gIFdlbGNvbWVMZXR0ZXIgPC4uIEN1c3RvbWVyXG4gIEFkZHJWZXJpZmljYXRpb24gPC4uIEN1c3RvbWVyXG4gIE9ic2VydmVyIDx8LS0gV2VsY29tZUxldHRlclxuICBPYnNlcnZlciA8fC0tIEFkZHJWZXJpZmljYXRpb25cblxuICBjbGFzcyBDdXN0b21lciB7XG4gICAgK2F0dGFjaCgpXG4gICAgK2RldGFjaCgpXG4gICAgK25vdGlmeSgpXG4gICAgK2dldFN0YXRlKClcbiAgICArc2V0U3RhdGUoKVxuICB9XG5cbiAgY2xhc3MgT2JzZXJ2ZXIge1xuICAgICt1cGRhdGUoaW46IEN1c3RvbWVyKVxuICB9XG5cbiAgY2xhc3MgV2VsY29tZUxldHRlclxuICBjbGFzcyBBZGRyVmVyaWZpY2F0aW9uIiwibWVybWFpZCI6eyJ0aGVtZSI6ImZvcmVzdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)