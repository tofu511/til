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
