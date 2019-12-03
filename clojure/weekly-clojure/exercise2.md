[4clojure](http://www.4clojure.com/problems)

### last element
```clojure
(defn l [value]
  (nth value (- (count value) 1)
  )
)
```

### Penultimate Element
```clojure
(defn penultimate [value]
   (nth value (- (count value) 2)))
```

### Nth Element
```clojure
(defn my-nth [value index]
  (last (take (+ index 1) value)
  )
)
```

### Count a Sequence
```clojure
(defn my-count [value]
  (if (empty? value) 0
  (inc (my-count (rest value)))
  )
)
```

### Sum It All Up
```clojure
(defn my-sum [value]
  (reduce + value)
)
```


### Find the odd numbers
```clojure
(defn my-odd? [value]
  (filter #(= (mod % 2) 1) value)
)
```

### Reverse a Sequence

```clojure
(defn my-rev [value]
  (reduce conj '() value))
```

### Palindrome Detector
```clojure
(defn palindrome? [value]
  (= (apply str (reverse value)) value)
  )
```

### Fibonacci Sequence
```clojure
(def fib-seq
 (lazy-cat [0 1] (map + (rest fib-seq) fib-seq)))

(defn fib [n]
 (take n fib-seq))
```

#### 参考資料
- [lazy-seq in Clojure (翻訳)](https://medium.com/@11Takanori/lazy-seq-in-clojure-%E7%BF%BB%E8%A8%B3-8fba0ebcb6af)
