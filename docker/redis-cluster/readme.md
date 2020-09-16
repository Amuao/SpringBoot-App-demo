### 需求
搭建一个三主三从模式的redis集群

### 环境以及工具
windows10  
Docker Desktop  
Redis 6.0.8镜像  
Another Redis Desktop Manager (这个工具可能有点问题，集群下显示的数据不准确，最好是进入容器命令行查看)  
节点理想配置如下： 
```
     192.168.200.11    7001   主节点 
     192.168.200.12    7002   主节点
     192.168.200.13    7003   主节点
     192.168.200.14    7004   从节点(主节点7001)
     192.168.200.15    7005   从节点(主节点7002)
     192.168.200.16    7006   从节点(主节点7003)
```
其实这个会随机分配还可能由于存在宕机问题可能主从之间会切换 
###步骤
1.首先创建redis网络的集群
```
    docker network create --subnet=192.168.200.0/24 redis-cluster-net
```
2.执行启动命令
``` 
 docker-compose up -d
 ```  
3.查看容器状态是否正常,而后进入随便一个主节点容器执行init.sh其中的命令


### 总结
1.本次搭建的是一个三主三从结构的Redis集群，主从节点按照init.sh命令中IP:PORT的顺序，先是3个主节点，然后是3个从节点。--cluster-replicas 1意味着们希望为每个创建的主机都提供一个从机。  
2.docker-compose配置文件中的&符号用来建立锚点（defaults），* 用来引用锚点，在这个例子中redis:latest中间不允许出现空格。  
3.docker-compose up -d命令会在后台创建容器，在down会删除掉创建的容器。  
4.第一次当容器启动后，去其中一个主节点执行init.sh的命令，这其中的ip也是宿主机器的，如果没有错误可以看到redis的插槽分配，而后使用redis-cli -p {port} 进入对应端口的redis，进行简单的set get测试主从关系，
当返回结果出现 (error) MOVED 7486 192.168.200.1:{port} 这种情况时，数据没有保存是正常现象，因为此时按照插槽分配，这个k-v不应该在这台redis实例上，所以redis返回一个MOVED类型，告诉客户端应该将它存储在192.168.200.1:{port}，即再去{port}上请求存储一次，这个功能应该由客户端完成。  
5.集群主节点会将集群信息保存在nodes.conf文件中。  
6.其中一台主节点宕机后从节点会升级为主节点，当宕机的主节点重新启动后会成为新的从节点，如果这对新主从节点同时挂掉那么整个集群不可用直到其中一个节点重新加入集群。
#### 参考
📕 ：Redis入门指南（第2版）  
[Redis cluster tutorial](https://redis.io/topics/cluster-tutorial)  
[使用Docker部署Redis集群——三主三从](https://jasonkayzk.github.io/2020/01/17/%E4%BD%BF%E7%94%A8Docker%E9%83%A8%E7%BD%B2Redis%E9%9B%86%E7%BE%A4-%E4%B8%89%E4%B8%BB%E4%B8%89%E4%BB%8E/)  
[Redis集群搭建--replicas 1](https://blog.csdn.net/mycsdnhome/article/details/88547402)  
