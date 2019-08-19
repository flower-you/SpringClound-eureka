##  SpringClound-eureka
使用springClound-eureka搭建了注册中心，缓存模块，商品模块,schedule是定时任务模块想单出出来使用quartz进行定时任务的统一管理。  注册中心由一个server模块开启两个进程，作为两个注册中心，互相注册，保证了服务的高可用，  在其中一个server宕机之后，还有一个server可以正常接收注册信息。  当商品信息发生变化时，商品模块同时使用fegin远程调用缓存模块更新缓存，确保mysql与redis缓存的一致性。

## 定时任务清空缓存
使用了三种方式：
（1）Spring的schedule

(2)ThreadPoolTaskScheduler

(3)Quartz（初步了解，实践有待完善）

前两种分布式，多节点任务执行时可能会出现冲突，比如多次启动，或者运行中的任务突然暂停的问题
所以增加了数据库的表，可以记录任务信息，在 启动，停止，更改任务执行周期 时先判断任务的运行状态。
保持了分布式下任务运行的一致性。

如果有多个定时任务，例如清各种缓存，定时发邮件，定时提醒等，为了便于任务的统一调度管理，
将定时任务单独作为一个模块schedule，管理任务，具体任务逻辑可以通过请求相应服务的接口来实现。
schedule 是想用quartz来实现的，具体实现看了一个demo，任务执行这块儿还有点不明白。https://github.com/simonsfan/springboot-quartz-demo

