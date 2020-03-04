# U盘辅助工具

## 项目简介

该项目用于作为易途杯参赛项目，开发周期为3天，本人主要负责除GUI外所有功能的实现

## 开发描述

- 前端使用swing
- 自动监听U盘插入
- 使用Soctet实现C/S架构实现文件传输，Server每收到一个简历连接的请求就创建一个新的线程对其进行处理
- 通过修改RandomAccessFile中的偏移量实现在指定位置读写
- 传输文件时将临时文件后缀改为.temp
- 当一个新的传输建立是服务器将会首先搜寻对应.temp文件是否存在，若不存在就创建它，若存在就像客户端发送当前已经接收的长度以实现断点续传
- 开发环境使用Oracle JDK8
- 服务器使用端口8899
- 打包使用exe4j

