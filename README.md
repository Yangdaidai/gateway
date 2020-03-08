## Spring Cloud Zuul 
- 网关、鉴权、流量转发、请求统计
###Zuul代理:
 - 默认情况下，Zuul会代理所有注册到Eureka Server的微服务，并且Zuul的路由规则如下：
  http://ZUUL_HOST:ZUUL_PORT/微服务在Eureka上的serviceId/**会被转发到serviceId对应的微服务。