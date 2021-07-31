1、https://blog.csdn.net/jiejiegua/article/details/112574106
该博客用定时器的方式，在每月最后1天，刷新sharding-sphere的
在创建表的时候 动态刷新 actual-data-nodes 实现动态创建表被shard加载

2、这个github可以：https://github.com/yinjihuan/sharding-jdbc

3、ShardingTableRuleActualTablesRefreshSchedule：定时任务，每个月最后1天，创建下个月的表，
并重新刷新tableRule(利用反射改变其属性)

MyPreciseShardingAlgorithm：自定义分片算法

4、该项目单库分表