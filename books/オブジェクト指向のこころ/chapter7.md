# Adapterパターン

## Adapterパターンとは
- あるクラスのインターフェースを、クライアントが望むインターフェースに変換する。Adapterによってクラス群は互換性のあるインターフェースを持つことになり、協調して動作できるようになる

|    |    |
| ---- | ---- |
| 目的 | 修正することのできない既存オブジェクトを、特定のインターフェースに適合させる |
| 問題| 使用したいデータや振る舞いが既存システム内に存在しているものの、そのインターフェースが正しくない場合。通常は、抽象クラスから何かしらの派生物を作成しなければならない場合に使用する |
| 解決策 | 必要なインターフェースを保持したラッパーをAdapterによって提供する |

---
- Before
    - ShapeからCircleを派生させたいが、既存のXXCircleは名前やパラメータリストが異なっており派生させることができない
```mermaid
classDiagram
    class Shape{
        +fill()
        +setColor()
        +undisplay()
    }

    class Point {
        +fill()
        +setColor()
        +undisplay()
    }

    class XXCircle {
        +fillIt()
        +setItsColor()
        +undisplayIt()
    }

    Shape <|-- Point
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG4gICAgY2xhc3MgU2hhcGV7XG4gICAgICAgICtmaWxsKClcbiAgICAgICAgK3NldENvbG9yKClcbiAgICAgICAgK3VuZGlzcGxheSgpXG4gICAgfVxuXG4gICAgY2xhc3MgUG9pbnQge1xuICAgICAgICArZmlsbCgpXG4gICAgICAgICtzZXRDb2xvcigpXG4gICAgICAgICt1bmRpc3BsYXkoKVxuICAgIH1cblxuICAgIGNsYXNzIFhYQ2lyY2xlIHtcbiAgICAgICAgK2ZpbGxJdCgpXG4gICAgICAgICtzZXRJdHNDb2xvcigpXG4gICAgICAgICt1bmRpc3BsYXlJdCgpXG4gICAgfVxuXG4gICAgU2hhcGUgPHwtLSBQb2ludCIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)
---
- After
    - Shapeから派生したCircleクラスを作る
    - CircleにXXCircleを保持させる
    - Circleオブジェクトに対するリクエストをXXCircleオブジェクトに転送する
```mermaid
classDiagram
    class Shape {
        +fill()
        +setColor()
        +undisplay()
    }

    class Point {
        +fill()
        +setColor()
        +undisplay()
    }

    class Circle {
        +fill()
        +setColor()
        +undisplay()
    }

    class XXCircle {
        +fillIt()
        +setItsColor()
        +undisplayIt()
    }

    Shape <|-- Point
    Shape <|-- Circle
    Circle *-- XXCircle
```
![](https://mermaid.ink/img/eyJjb2RlIjoiY2xhc3NEaWFncmFtXG4gICAgY2xhc3MgU2hhcGUge1xuICAgICAgICArZmlsbCgpXG4gICAgICAgICtzZXRDb2xvcigpXG4gICAgICAgICt1bmRpc3BsYXkoKVxuICAgIH1cblxuICAgIGNsYXNzIFBvaW50IHtcbiAgICAgICAgK2ZpbGwoKVxuICAgICAgICArc2V0Q29sb3IoKVxuICAgICAgICArdW5kaXNwbGF5KClcbiAgICB9XG5cbiAgICBjbGFzcyBDaXJjbGUge1xuICAgICAgICArZmlsbCgpXG4gICAgICAgICtzZXRDb2xvcigpXG4gICAgICAgICt1bmRpc3BsYXkoKVxuICAgIH1cblxuICAgIGNsYXNzIFhYQ2lyY2xlIHtcbiAgICAgICAgK2ZpbGxJdCgpXG4gICAgICAgICtzZXRJdHNDb2xvcigpXG4gICAgICAgICt1bmRpc3BsYXlJdCgpXG4gICAgfVxuXG4gICAgU2hhcGUgPHwtLSBQb2ludFxuICAgIFNoYXBlIDx8LS0gQ2lyY2xlXG4gICAgQ2lyY2xlICotLSBYWENpcmNsZSIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)

## FacadeとAdapterの共通点と違い
- FacadeとAdapterはいずれも既存クラスを使用する
- Facadeでは既存インターフェースを設計し直す必要がない。Adapterパターンではインターフェースを設計し直す必要がある
- Facadeではポリモーフィズムに則った振る舞いは不要であるが、Adapterではおそらく必要になる。
- Facadeの目的はインターフェースを簡素化するため。Adapterの本来の目的は既存インターフェースの再設計。
- Facadeはインターフェースの簡素化
- Adapterは既存インターフェースを他のインターフェースに変換する