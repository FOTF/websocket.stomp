Spring webSocket使用注解的方式在jetty下在应用

注意事项：
1、启用jetty的websocker模块
2、spring版本要4.0+
3、jetty版本要9.1+


错误排除：
1、
错误提示：No suitable default RequestUpgradeStrategy found
排查原因：可能是服务器没有启动web_socket模块

2、
错误提示：org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
No qualifying bean of type [org.springframework.core.task.TaskExecutor] 
is defined: expected single matching bean but found 4: 
clientOutboundChannelExecutor,messageBrokerTaskScheduler,
clientInboundChannelExecutor,brokerChannelExecutor

排查原因，查看是否有这段代码：
<task:annotation-driven/>
如果有去除就好。


本测试代码为gradle项目，直接导入即可运行。
测试访问页面为:127.0.0.1/index.html