```
** ウェイウェーイ問題

*** Level1
3で割り切れる場合は「ウェイ」と表示
5で割り切れる場合は「ウェーイ」と表示
3と5の両方で割り切れる場合は「ウェイウェーイ」と表示
それ以外の場合はその数字を表示

*** Level2
Level1の条件に以下を追加します。

2の倍数をスキップ
たとえば6の場合、3で割り切れるので「ウェイ」と表示するところですが、2の倍数なのでスキップして表示しません。

*** Level3
Level2は1行毎に1つ表示していましたが、1行にすべてをカンマ(,)区切りで表示してください。
```

#### Level1

```clojure
(defn wei-weei [n]
 (cond
  (= 0 (mod n 15)) "ウェイウェーイ"
  (= 0 (mod n 5)) "ウェーイ"
  (= 0 (mod n 3)) "ウェイ"
  :else (str n)
 )
)
```

#### Level2

```clojure
(println (map wei-weei (filter odd? (range 50))))
```

#### Level3

```clojure
(println (clojure.string/join ", " (map wei-weei (filter odd? (range 50)))))
```
