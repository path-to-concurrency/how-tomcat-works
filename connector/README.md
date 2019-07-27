### Servlet


### Tasking
[ ] connector single thread, 请求生产者
[ ] http processor
[ ] processor, resource 处理
[ ] 对象池模式

### Code Smells
[ ] Connector 需要知道 Container 的存在，Container 需要知道 Servlet 存在，是否改成生产消费者模式，使用事件驱动的方式完成。
当某一层的逻辑复杂了，可以通过抽象一层，封装相同逻辑。


先 happy 流程能通，在演进看。
先看整体架构，写代码，落地架构。然后引入并发设计。

后续可以通过看文章完成？


[x] https://blog.csdn.net/cun_chen/article/details/69980169
先分析整体处理时间, 统计模块花费的时间，比如：time(Connector) + time(Processor)。

每个阶段，考虑如何实现并发。 比如：Connector 多线程化，Processor 多线程化。

要做 多线程 Processor，需要对 Processor 实例进行管理。

实现***池化模型***， ***任务委派模型伪异步交互模型***。！！！


z 字模型
