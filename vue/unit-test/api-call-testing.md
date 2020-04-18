# APIのテスト
- APIのモックには`jest.mock()`を使う
- `jest.mock()`ではモジュールをモックし、APIコールするメソッドで`.mockResolvedValueOnce()`を使用するとモックした値が取得できる
- 非同期の処理をテストする際には`flush-promises`ライブラリの`flushPromises()`を使用する
- APIの呼び出し回数は`toHaveBeenCalledTimes()`でテスト(アサーション)することができる
- `jest.mock()`ではAPIの呼び出し回数がテストごとにリセットされないので、`beforeEach`で`jest.clearAllMocks()`を呼ぶことで、テストごとにAPIの呼び出し回数を正しくテストできる

## 参考
- https://www.vuemastery.com/courses/unit-testing/testing-api-calls