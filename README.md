# seno 限时秒杀系统

## 项目描述
seno是基于Spring Boot+MyBatis框架开发的限时秒杀系统，针对秒杀业务本身的需求和瞬时并发量大的特点做出了一系列优化：

- 使用Redis作为缓存，用于缓存session(实现session共享)、缓存列表页面等频繁访问的热点数据、预减商品库存(减轻数据库访问压力)、缓存用户访问秒杀接口的次数并设置有效期(防止恶意刷接口)。
- 使用消息队列RabbitMQ实现异步下单，缓冲瞬时流量。
- 使用MD5加密随机串生成动态url，隐藏秒杀地址。

## 图片演示
登录
![Image text](https://github.com/gloriaaaa/seno/blob/master/imgs/login.png)

商品列表
![Image text](https://github.com/gloriaaaa/seno/blob/master/imgs/goods_list.png)

秒杀成功
![Image text](https://github.com/gloriaaaa/seno/blob/master/imgs/miaoshasuccess.png)

订单详情
![Image text](https://github.com/gloriaaaa/seno/blob/master/imgs/order.png)




