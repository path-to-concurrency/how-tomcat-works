### Servlet


### Tasking
[ ] connector , 请求生产者
[ ] processor, resource 处理

### Code Smells
[ ] Connector 需要知道 Container 的存在，Container 需要知道 Servlet 存在，是否改成生产消费者模式，使用事件驱动的方式完成。
当某一层的逻辑复杂了，可以通过抽象一层，封装相同逻辑。
