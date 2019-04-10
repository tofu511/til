## Compute Options
- 3種類ある
- Google AppEngine(PaaS)
  - serverless & ops-free
- Google ComputeEngine(IaaS)
  - OSより上は全てコントロールできる
- Google KubernetesEngine
  - コンテナを使う場合

### Hosting a Website
#### Static, No SSL
- GoogleCloudStorageを使うのが一番シンプル
- ドメインを取ることもできる

#### SSL, CDNなどを使う場合
- firebase hosting + GoogleCloudStorage

#### Load Balancing, scaling
- load balancing, scalingをコントロールしたい、設定などもコントロールしたい場合はGoogleComputeEngine(GCE)
- Google Cloud Launcher を使えば、LAMPやWordpressなどがすぐ作れる
- storage optionはいくつかある
  - cloud storage buckets
  - standard persistent disks
  - SSD(solid state disks)
  - local SSD
- Load Balancing option
  - Network load balancing
    - forwarding rules based on address, port, protocol
  - HTTP load balancing


## 参考資料
- https://www.udemy.com/gcp-data-engineer-and-cloud-architect/learn/v4/t/lecture/7589588?start=0
