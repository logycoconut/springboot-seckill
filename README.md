# 设计一个秒杀系统

> * 基于秒杀场景的Java高并发实现
* 对秒杀场景的方案设计

## 技术体系

* 前端: `Bootstrap` + `jQuery`

* 后端: `SpringBoot2` + `MyBatisPlus` + `MySQL`

* 中间件技术: `Redis` + `RabbitMQ` + `JWT`

## 秒杀场景以及解决方案

### 高并发

> 在传统WEB应用中, 每次请求都是对数据库的一次访问; 但是在秒杀场景下, 流量会在瞬间达到上万以上, 这么多请求要是都流向数据库, 数据库立马就会挂掉 秒杀活动被迫中断

所以我们应该尽量将请求拦截在上游, 对请求进行限流

**前端限流 :** 

* 秒杀前将秒杀按钮置灰 ( 即不可点击的状态 ) 
* 秒杀时限制点击频率, 例如每秒最多点击一次

**后端限流 :**

* 令牌桶算法限流, 例如 `Guava` 的 `RateLimiter`, 就是以固定的频率向桶中放入令牌, 每次秒杀前都从桶中获取令牌, 取到令牌后才能进入下一步, 否则返回错误代码

**库存预热**

* 在服务启动时将秒杀相关信息都加载到`Redis`中, 商品库存的删减都在`Redis`中进行

**消息队列**

* 用户只关心当时是否成功秒杀, 对于后台数据库真实库存的变化以及订单创建快慢并不敏感
* 运行消息队列缓冲过量流量, 将同步操作转换成异步推送, 在队列的另一端平滑地将消息推送出去,  平稳的对数据库进行访问

**资源静态化**

* 本项目并未实现完全的前后端分离, 所有h5页面均由`thymeleaf`渲染后抛给前端, 所以我们先渲染网页缓存到`Redis`中, 并设置过期时间, 请求的时候直接从`Redis`中获取

### 超卖

* 对商品库存加乐观锁

### 链接暴露

* 秒杀链接的提前暴露可能会导致有人绕过业务直接请求接口,为了防止这种情况发生, 我们可以采取URL动态化的方式
* **具体实现思路 :** 在秒杀开始之前, 先获取秒杀地址, 再根据秒杀地址进行秒杀

### 恶意请求 

* 采取验证码的方式防止大量重复请求, 同时也可以分担流量压力


## 致谢

* 本项目是对 [zaiyunduan123/springboot-seckill](https://github.com/zaiyunduan123/springboot-sckill) 的二次开发, 在项目初期给了我很大的启发

* 在后期寻求再次优化的时候, 敖丙的文章也让我对秒杀系统的整体架构有了更清楚的认知, [敖丙带你设计【秒杀系统】](https://mp.weixin.qq.com/s?__biz=MzAwNDA2OTM1Ng==&mid=2453145142&idx=1&sn=3a25d7caf7135a64112e4f9ac2224a9e&chksm=8cfd24b5bb8aada34295666b6b4eace58efa44cd2a9f07c06ca2a61242583e83cea3d1b98143&scene=158#rd)