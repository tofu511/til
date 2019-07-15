## IAMロールとは
- 特定のアクセス権限を持ち、アカウントを作成できるIAMアイデンティティのこと
- IAMユーザは1人の特定の人に一意に関連づけられるが、ロールはそれを必要とする任意の人が引き受けるようになっている
- ロールにはパスワードやアクセスキーは関連づけられない
- パスワードやアクセスキーの代わりにロールセッション用の一時的なセキュリティ認証情報が提供される
- ロールを使用すると通常はAWSリソースにアクセス権がないユーザー、アプリケーション、サービスにアクセス権を委任できる

## IAMロールを構成する要素
- RoleName
- Path
- AssumeRolePolicyDocument

## AssumeRoleとは
- **第三者に自分のAWSアカウントのAPI権限を委譲する仕組み**
- RoleArnを入力としてCredentialsを返すAPIのこと
- ロールに設定された権限を持った一時キーを入手する

## AssumeRolePolicyDocument
- `Principal`部分に信頼するアカウントやサービスを記載することで引き受け(Assume)を許可する

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "AWS": [ "123456789012" ] //AWSアカウントを信頼
        "Service": [ "ec2.amazonaws.com" ] // EC2サービスを信頼
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
```

## 参考資料
- [IAM ロール](https://docs.aws.amazon.com/ja_jp/IAM/latest/UserGuide/id_roles.html)
- [IAMロール徹底理解 〜 AssumeRoleの正体](https://dev.classmethod.jp/cloud/aws/iam-role-and-assumerole/)
- [Assume Roleの用途・メリット](https://christina04.hatenablog.com/entry/assume-role)
