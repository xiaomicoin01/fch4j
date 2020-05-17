# fchJ
针对FCH的RPC框架，封装了全部的RPC接口，同时自带协议（FCH定义的所有协议）解析的框架，目前只实现了CID的解析，
别的协议协议暂时开发者没有搞明白，稍后会添加进去，这里封装这个组件，只是为了让使用java开发人员更方便的
针对FCH做自己的业务，如果有问题或者bug请联系本人,本人邮箱：wanglin5659643@163.com


# 模块
````
fch-common  通用的工具类
fch-core    核心包
fch-api     自定义处理API，主要用于解析FCH的自定义协议，以及协议内容的上链操作
fch-rpc     钱包的RPC

````
# 使用方法

配置钱包
```
rpc:
  protocol: http
  host: 127.0.0.1
  port: 34452
  user: darkcoinrpc
  password: 123gfjk3R3pCCVjHtbRde2s5kzdf233sa
  scheme: Basic                         
```
 将上述配置封装到Properties中，初始化FCH的客户端
 ```
 CloseableHttpClient httpProvider = resourceUtils.getHttpProvider();
 Properties nodeConfig = resourceUtils.getNodeConfig();
 FchdClient client = new VerboseFchdClientImpl(httpProvider, nodeConfig);
 ```
然后通过client就可以想使用钱包一样来获取钱包的任何信息（这里封装了freecash的所有命令）


# 自定义协议解析
>实现接口：IAnalysisData
```
String getType(); 返回协议类型，例如：FEIP
void analysis(String protocolValue); 参数为FCH协议体，例如：FEIP|3|1|wanglin|#本人|Vip_nMeT
  
```
  
# 说明
```
1.本工程采用spring-cloud微服务模式拆解模块，使用前需要对spring-cloud有所了解
2.本人开发这个模块是为了更好的做上层的应用，主要是为知识库的提供底层支撑
3.开发比较匆忙，可能会有bug，如发现请联系本人，不胜感激
```