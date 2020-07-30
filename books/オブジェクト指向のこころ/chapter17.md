# Decoratorパターン

## Decoratorパターンとは
- オブジェクトに対して新たな責務を動的に付加する。Decoratorによって、サブクラス化による機能拡張よりも柔軟に代替案を実現できる

|    |    |
| ---- | ---- |
| 目的 | オブジェクトに対して新たな責務を動的に付加する |
| 問題| 手元にあるオブジェクトには基本的な機能が用意されているものの、そのオブジェクトの基本機能を実行する前や後に、何らかの追加機能を付加したい場合に使用できる。Javaの基本APIでは、I/O関連でDecoratorパターンが頻繁に使用されている |
| 解決策 | サブクラスを作成せずとも、オブジェクトに機能を追加することができる |
| 構成要素と協調要素 | ConcreteComponentはDecoratorによって機能の付加が行われるクラスである。ConcreteComponentからクラスを派生させることによって、核となる機能が提供される場合もしばしばある。この場合、ConcreteComponentは、その名前とは裏腹に抽象クラスとなる。Componentはこういったクラス全てのインターフェースを定義する
| 因果関係 | 付加する機能は、個々のオブジェクト内に実装される。これにより、ConcreteComponentが持つ機能の前後に動的に機能を付加できるという利点が生み出される。なおDecoratorパターンによって、核となる前後に追加機能が付加できるようになるが、連鎖の実体は常にConcreteComponentで終わることになるという点には注意 |
| 実装 | 核となる機能を表したクラスとそのクラスに付加する追加機能の双方を表す抽象クラスを作成する。Decoratorの実装では、正しい順序で呼び出しが行われるよう、後続の機能を呼び出す前か後に追加機能を呼び出す | 

```mermaid
classDiagram

  Component <|-- ConcreteComponent
  Component <|-- Decorator

  Decorator "0.1" o-- "1" Component

  Decorator <|-- ConcreteDecoratorA
  Decorator <|-- ConcreteDecoratorB

  class Component {
    Operation()
  }

  class ConcreteComponent {
    Operation()
  }

  class Decorator {
    Operation()
  }

  class ConcreteDecoratorA {
    addedState
    Operation()
  }

  class ConcreteDecoratorB {
    Operation()
    AddedBehavior()
  }
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cbiAgQ29tcG9uZW50IDx8LS0gQ29uY3JldGVDb21wb25lbnRcbiAgQ29tcG9uZW50IDx8LS0gRGVjb3JhdG9yXG5cbiAgRGVjb3JhdG9yIFwiMC4xXCIgby0tIFwiMVwiIENvbXBvbmVudFxuXG4gIERlY29yYXRvciA8fC0tIENvbmNyZXRlRGVjb3JhdG9yQVxuICBEZWNvcmF0b3IgPHwtLSBDb25jcmV0ZURlY29yYXRvckJcblxuICBjbGFzcyBDb21wb25lbnQge1xuICAgIE9wZXJhdGlvbigpXG4gIH1cblxuICBjbGFzcyBDb25jcmV0ZUNvbXBvbmVudCB7XG4gICAgT3BlcmF0aW9uKClcbiAgfVxuXG4gIGNsYXNzIERlY29yYXRvciB7XG4gICAgT3BlcmF0aW9uKClcbiAgfVxuXG4gIGNsYXNzIENvbmNyZXRlRGVjb3JhdG9yQSB7XG4gICAgYWRkZWRTdGF0ZVxuICAgIE9wZXJhdGlvbigpXG4gIH1cblxuICBjbGFzcyBDb25jcmV0ZURlY29yYXRvckIge1xuICAgIE9wZXJhdGlvbigpXG4gICAgQWRkZWRCZWhhdmlvcigpXG4gIH1cblxuIiwibWVybWFpZCI6eyJ0aGVtZSI6ImZvcmVzdCJ9fQ)

### 例
- Eコマースの注文に対して伝票をプリントするケースを考える
    - 伝票のヘッダー、フッターをいろいろ付加する

```mermaid
classDiagram

  Component <.. Client
  Component <|-- SalesTicket
  Component <|-- TicketDecorator
  TicketDecorator "0..1" o-- "1" Component

  TicketDecorator <|-- Header1
  TicketDecorator <|-- Header2
  TicketDecorator <|-- Footer1
  TicketDecorator <|-- Footer2

  class Client
  class Component {
    +printTicket()
  }
  class SalesTicket {
    +printTicket()
  }
  class TicketDecorator {
    -Component myComponent
    +printTicket()
  }

  class Header1 {
    +printTicket()
    -printHeader()
  }
  
  class Header2 {
    +printTicket()
    -printHeader()
  }

  class Footer1 {
    +printTicket()
    -printFooter()
  }

  class Footer2 {
    +printTicket()
    -printFooter()
  }
```

![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cbiAgQ29tcG9uZW50IDwuLiBDbGllbnRcbiAgQ29tcG9uZW50IDx8LS0gU2FsZXNUaWNrZXRcbiAgQ29tcG9uZW50IDx8LS0gVGlja2V0RGVjb3JhdG9yXG4gIFRpY2tldERlY29yYXRvciBcIjAuLjFcIiBvLS0gXCIxXCIgQ29tcG9uZW50XG5cbiAgVGlja2V0RGVjb3JhdG9yIDx8LS0gSGVhZGVyMVxuICBUaWNrZXREZWNvcmF0b3IgPHwtLSBIZWFkZXIyXG4gIFRpY2tldERlY29yYXRvciA8fC0tIEZvb3RlcjFcbiAgVGlja2V0RGVjb3JhdG9yIDx8LS0gRm9vdGVyMlxuXG4gIGNsYXNzIENsaWVudFxuICBjbGFzcyBDb21wb25lbnQge1xuICAgICtwcmludFRpY2tldCgpXG4gIH1cbiAgY2xhc3MgU2FsZXNUaWNrZXQge1xuICAgICtwcmludFRpY2tldCgpXG4gIH1cbiAgY2xhc3MgVGlja2V0RGVjb3JhdG9yIHtcbiAgICAtQ29tcG9uZW50IG15Q29tcG9uZW50XG4gICAgK3ByaW50VGlja2V0KClcbiAgfVxuXG4gIGNsYXNzIEhlYWRlcjEge1xuICAgICtwcmludFRpY2tldCgpXG4gICAgLXByaW50SGVhZGVyKClcbiAgfVxuICBcbiAgY2xhc3MgSGVhZGVyMiB7XG4gICAgK3ByaW50VGlja2V0KClcbiAgICAtcHJpbnRIZWFkZXIoKVxuICB9XG5cbiAgY2xhc3MgRm9vdGVyMSB7XG4gICAgK3ByaW50VGlja2V0KClcbiAgICAtcHJpbnRGb290ZXIoKVxuICB9XG5cbiAgY2xhc3MgRm9vdGVyMiB7XG4gICAgK3ByaW50VGlja2V0KClcbiAgICAtcHJpbnRGb290ZXIoKVxuICB9IiwibWVybWFpZCI6eyJ0aGVtZSI6ImZvcmVzdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)

- ソースコードはDecorator.scalaに書きました

## メモ
- Decoratorパターンは以下の状況を解決する 
  - 複数の追加機能が存在している
  - 追加機能群が規則にしたがっているか分からない
  - クライアントに余計なことを考えさせずに追加機能を特定の順序で付加する必要がある
  - どの追加機能を使用しているのかアプリケーション側に意識させたくない