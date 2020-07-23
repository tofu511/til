# Bridgeパターン

## Bridgeパターンとは
- 実装から抽象的側面を切り出して、それらを独立して変更できるようにする

|    |    |
| ---- | ---- |
| 目的 | 実装を使用しているオブジェクト群から、その一連の実装を切り離す |
| 問題|  クラス数の爆発を招くことなく、抽象クラスの派生物から複数の実装を使用する必要がある |
| 解決策 | 使用する全ての実装のインターフェースを定義し、抽象クラスの派生物からそれを使用する |
| 構成要素と協調要素| Abstractionは実装されるオブジェクトのためのインターフェースを定義する。Implementorは特定実装クラスのインターフェースを定義する。Abstractionから派生したクラス(RefinedAbstraction)は、Implementorから派生したクラスがどの具体的なConcreteImplementorであるかを知ることなくそれを使用する
| 因果関係 | オブジェクトが使用している実装を、そのオブジェクトから切り離すことによって拡張性を高める。クライアントオブジェクトは実装に関するあれこれを気にする必要がなくなる |
| 実装 | 抽象クラス内に実装をカプセル化する。実装を行う抽象的側面の基底クラス内にそのハンドルを保持する |

```mermaid
classDiagram

    class Abstraction {
        operation()
    }
    class RefinedAbstraction
    
    class Implementor {
        OperationImpl()
    }
    class ConcreteImplementorA {
        +OperationImpl()
    }
    class ConcreteImplementorB {
        +OperationImpl()
    }

    Abstraction <|-- RefinedAbstraction
    Abstraction o-- Implementor
    Implementor <|-- ConcreteImplementorA
    Implementor <|-- ConcreteImplementorB

```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cbiAgICBjbGFzcyBBYnN0cmFjdGlvbiB7XG4gICAgICAgIG9wZXJhdGlvbigpXG4gICAgfVxuICAgIGNsYXNzIFJlZmluZWRBYnN0cmFjdGlvblxuICAgIFxuICAgIGNsYXNzIEltcGxlbWVudG9yIHtcbiAgICAgICAgT3BlcmF0aW9uSW1wbCgpXG4gICAgfVxuICAgIGNsYXNzIENvbmNyZXRlSW1wbGVtZW50b3JBIHtcbiAgICAgICAgK09wZXJhdGlvbkltcGwoKVxuICAgIH1cbiAgICBjbGFzcyBDb25jcmV0ZUltcGxlbWVudG9yQiB7XG4gICAgICAgICtPcGVyYXRpb25JbXBsKClcbiAgICB9XG5cbiAgICBBYnN0cmFjdGlvbiA8fC0tIFJlZmluZWRBYnN0cmFjdGlvblxuICAgIEFic3RyYWN0aW9uIG8tLSBJbXBsZW1lbnRvclxuICAgIEltcGxlbWVudG9yIDx8LS0gQ29uY3JldGVJbXBsZW1lbnRvckFcbiAgICBJbXBsZW1lbnRvciA8fC0tIENvbmNyZXRlSW1wbGVtZW50b3JCIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)

---
## 具体例
- ある形状を描画するシステムを考える
- 形状の種類と描画方法が流動的要素になる
    - 四角を描画
    - 丸を描画...など

### 継承を使ったアプローチ
- 形状や描画方法が増えるたびに組み合わせ(クラス数)が指数的に増える

```mermaid
classDiagram

    Shape <.. Client
    Shape <|-- V1Shape
    Shape <|-- V2Shape
    
    V1Shape <|-- V1Rectangle
    V1Shape <|-- V1Circle

    V2Shape <|-- V2Rectangle
    V2Shape <|-- V2Circle

    DP1 <.. V1Shape
    DP2 <.. V2Shape

    class Client
    class Shape {
        +draw()
    }
    class V1Shape {
        #drawLine()
        #drawCircle()
    }
    class V2Shape {
        #drawLine()
        #drawCircle()
    }
    class V1Rectangle {
        +draw()
    }
    class V1Circle {
        +draw()
    }
    class V2Rectangle {
        +draw()
    }
    class V2Circle {
        +draw()
    }

    class DP1 {
        +draw_a_line()
        +draw_a_circle()
    }
    class DP2 {
        +drawline()
        +drawcircle()
    }
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cbiAgICBTaGFwZSA8Li4gQ2xpZW50XG4gICAgU2hhcGUgPHwtLSBWMVNoYXBlXG4gICAgU2hhcGUgPHwtLSBWMlNoYXBlXG4gICAgXG4gICAgVjFTaGFwZSA8fC0tIFYxUmVjdGFuZ2xlXG4gICAgVjFTaGFwZSA8fC0tIFYxQ2lyY2xlXG5cbiAgICBWMlNoYXBlIDx8LS0gVjJSZWN0YW5nbGVcbiAgICBWMlNoYXBlIDx8LS0gVjJDaXJjbGVcblxuICAgIERQMSA8Li4gVjFTaGFwZVxuICAgIERQMiA8Li4gVjJTaGFwZVxuXG4gICAgY2xhc3MgQ2xpZW50XG4gICAgY2xhc3MgU2hhcGUge1xuICAgICAgICArZHJhdygpXG4gICAgfVxuICAgIGNsYXNzIFYxU2hhcGUge1xuICAgICAgICAjZHJhd0xpbmUoKVxuICAgICAgICAjZHJhd0NpcmNsZSgpXG4gICAgfVxuICAgIGNsYXNzIFYyU2hhcGUge1xuICAgICAgICAjZHJhd0xpbmUoKVxuICAgICAgICAjZHJhd0NpcmNsZSgpXG4gICAgfVxuICAgIGNsYXNzIFYxUmVjdGFuZ2xlIHtcbiAgICAgICAgK2RyYXcoKVxuICAgIH1cbiAgICBjbGFzcyBWMUNpcmNsZSB7XG4gICAgICAgICtkcmF3KClcbiAgICB9XG4gICAgY2xhc3MgVjJSZWN0YW5nbGUge1xuICAgICAgICArZHJhdygpXG4gICAgfVxuICAgIGNsYXNzIFYyQ2lyY2xlIHtcbiAgICAgICAgK2RyYXcoKVxuICAgIH1cblxuICAgIGNsYXNzIERQMSB7XG4gICAgICAgICtkcmF3X2FfbGluZSgpXG4gICAgICAgICtkcmF3X2FfY2lyY2xlKClcbiAgICB9XG4gICAgY2xhc3MgRFAyIHtcbiAgICAgICAgK2RyYXdsaW5lKClcbiAgICAgICAgK2RyYXdjaXJjbGUoKVxuICAgIH0iLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)

### Bridgeパターンのアプローチ
- 形状の抽象的側面(Shapeクラスの抽象的側面)と描画の実装(Drawingの実装)を切り離している
- 実装をオブジェクトから使用するものという観点に立つことで、実装における流動的要素を隠蔽できる

```mermaid
classDiagram

    Shape <|-- Rectangle
    Shape <|-- Circle
    Shape o-- Drawing
    Drawing <|-- V1Drawing
    Drawing <|-- V2Drawing
    DP1 <.. V1Drawing
    DP2 <.. V2Drawing

    class Shape {
        +draw()
        #drawLine()
        #drawCircle()
    }
    class Rectangle {
        +draw()
    }
    class Circle {
        +draw()
    }

    class Drawing {
        +drawLine()
        +drawCircle()
    }
    class V1Drawing {
        +drawLine()
        +drawCircle()
    }
    class V2Drawing {
        +drawLine()
        +drawCircle()
    }

    class DP1 {
        +draw_a_line()
        +draw_a_circle()
    }
    class DP2 {
        +drawline()
        +drawcircle()
    }
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG5cbiAgICBTaGFwZSA8fC0tIFJlY3RhbmdsZVxuICAgIFNoYXBlIDx8LS0gQ2lyY2xlXG4gICAgU2hhcGUgby0tIERyYXdpbmdcbiAgICBEcmF3aW5nIDx8LS0gVjFEcmF3aW5nXG4gICAgRHJhd2luZyA8fC0tIFYyRHJhd2luZ1xuICAgIERQMSA8Li4gVjFEcmF3aW5nXG4gICAgRFAyIDwuLiBWMkRyYXdpbmdcblxuICAgIGNsYXNzIFNoYXBlIHtcbiAgICAgICAgK2RyYXcoKVxuICAgICAgICAjZHJhd0xpbmUoKVxuICAgICAgICAjZHJhd0NpcmNsZSgpXG4gICAgfVxuICAgIGNsYXNzIFJlY3RhbmdsZSB7XG4gICAgICAgICtkcmF3KClcbiAgICB9XG4gICAgY2xhc3MgQ2lyY2xlIHtcbiAgICAgICAgK2RyYXcoKVxuICAgIH1cblxuICAgIGNsYXNzIERyYXdpbmcge1xuICAgICAgICArZHJhd0xpbmUoKVxuICAgICAgICArZHJhd0NpcmNsZSgpXG4gICAgfVxuICAgIGNsYXNzIFYxRHJhd2luZyB7XG4gICAgICAgICtkcmF3TGluZSgpXG4gICAgICAgICtkcmF3Q2lyY2xlKClcbiAgICB9XG4gICAgY2xhc3MgVjJEcmF3aW5nIHtcbiAgICAgICAgK2RyYXdMaW5lKClcbiAgICAgICAgK2RyYXdDaXJjbGUoKVxuICAgIH1cblxuICAgIGNsYXNzIERQMSB7XG4gICAgICAgICtkcmF3X2FfbGluZSgpXG4gICAgICAgICtkcmF3X2FfY2lyY2xlKClcbiAgICB9XG4gICAgY2xhc3MgRFAyIHtcbiAgICAgICAgK2RyYXdsaW5lKClcbiAgICAgICAgK2RyYXdjaXJjbGUoKVxuICAgIH1cbiIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)

## メモ
- Bridgeパターンを設計する際は抽象的側面を表す部分(派生クラスを含む)と実装を表す部分を心に留めること