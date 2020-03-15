let data = { price: 5, quantity: 2 }
let target = null

class Dep {
    constructor () {
        this.subscribers = []
    }
    // 変更を記録する
    depend () {
        if (target && !this.subscribers.includes(target)) {
            this.subscribers.push(target)
        }
    }
    // 変更を反映させる
    notify () {
        this.subscribers.forEach(sub => sub())
    }
}

let deps = new Map()
Object.keys(data).forEach(key => {
    deps.set(key, new Dep())
})

let dataWithoutProxy = data
// https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Proxy
data = new Proxy(dataWithoutProxy, {
    get (obj, key) {
        deps.get(key).depend()
        return obj[key]
    },
    set (obj, key, newVal) {
        obj[key] = newVal
        deps.get(key).notify()
        return true
    }
})

function wather (myFunc) {
    target = myFunc
    target()
    target = null
}

let total = 0

wather(() => {
    total = data.price * data.quantity
})

console.log("total = " + total)
data.price = 20
console.log("total = " + total)
data.quantity = 10
console.log("total = " + total) 

deps.set('discount', new Dep())
data['discount'] = 5

let salePrice = 0

wather(() => {
    salePrice = data.price - data.discount
})

console.log(`salePrice = ${salePrice}`)
data.discount = 7.5
console.log(`salePrice = ${salePrice}`)