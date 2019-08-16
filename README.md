# SpringClound-eureka
使用springClound-eureka搭建了注册中心，缓存模块，商品模块,schedule是定时任务模块想单出出来使用quartz进行定时任务的统一管理。  注册中心由一个server模块开启两个进程，作为两个注册中心，互相注册，保证了服务的高可用，  在其中一个server宕机之后，还有一个server可以正常接收注册信息。  当商品信息发生变化时，商品模块同时使用fegin远程调用缓存模块更新缓存，确保mysql与redis缓存的一致性。  
