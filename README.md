# DreamSpider
distributed multi-thread web spider.

本爬虫借用了Hazelcast这样的分布式数据结构，实现了多机并爬的效果。

在第一版实现的中，主要使用的是Java多线程的技术，爬虫只能在单机（一个JVM）中执行；
在第二版的实现中，借鉴了Hazelcast框架，实现了多机（多个JVM）同时爬取的效果；
在第三版的实现中，借鉴了Spring框架的设计理念，重用了很多设计模式和原则，对原来的代码进行了重构。

在后面的规划中，希望能够实现断点续爬、解析数据等重要功能。
