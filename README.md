# 智识图库 - 后端服务

一个功能丰富的企业级智能图库管理系统，支持图片上传、管理、搜索、AI扩图、协同编辑等功能。

## 项目简介

智识图库是一个基于 Spring Boot 开发的图片管理平台，提供完整的图片生命周期管理，包括上传、存储、审核、搜索、分享等功能。系统采用分库分表设计，支持私有空间和团队协作空间两种模式。

## 技术栈

- **核心框架**: Spring Boot 2.7.6
- **JDK 版本**: Java 11
- **数据库**: MySQL + MyBatis-Plus 3.5.9
- **分库分表**: Apache ShardingSphere 5.2.0
- **缓存**: Redis + Caffeine 本地缓存
- **权限认证**: Sa-Token 1.39.0
- **对象存储**: 腾讯云 COS
- **AI 能力**: 阿里云 AI 扩图
- **实时通信**: WebSocket + Disruptor 高性能队列
- **接口文档**: Knife4j (Swagger)
- **工具库**: Hutool 5.8.26

## 核心功能

### 用户管理
- 用户注册、登录、登出
- 基于 Sa-Token 的权限认证
- 支持管理员和普通用户角色

### 图片管理
- 单张/批量上传图片（支持文件和URL上传）
- 图片审核流程（待审核、通过、拒绝）
- 图片编辑（名称、标签、分类等）
- 批量编辑和删除
- 以图搜图功能
- 按颜色搜索图片

### 空间管理
- 私有空间：个人图片存储
- 团队空间：多人协作共享
- 空间容量和级别管理
- 空间成员权限管理（管理员/编辑者/浏览者）

### 空间分析
- 空间使用情况分析
- 图片分类统计
- 标签分析
- 图片尺寸分布
- 用户上传行为分析
- 空间排名分析

### AI 功能
- 阿里云 AI 智能扩图
- 图片编辑协同（WebSocket 实时同步）

### 高级特性
- 分库分表支持（按空间ID分表）
- 多级缓存（Redis + Caffeine）
- 分布式 Session
- 图片爬虫批量抓取

## 项目结构

```
zhishi-picture-backend/
├── src/main/java/com/zhishi/picture/
│   ├── annotation/          # 自定义注解
│   ├── aop/                 # AOP 切面
│   ├── api/                 # 第三方 API 封装
│   │   ├── aliyunai/        # 阿里云 AI
│   │   └── imagesearch/     # 以图搜图
│   ├── common/              # 通用类
│   ├── config/              # 配置类
│   ├── constant/            # 常量
│   ├── controller/          # 控制器层
│   ├── exception/           # 异常处理
│   ├── manager/             # 业务管理器
│   │   ├── auth/            # 权限管理
│   │   ├── sharding/        # 分库分表
│   │   ├── upload/          # 文件上传
│   │   └── websocket/       # WebSocket
│   ├── mapper/              # 数据访问层
│   ├── model/               # 数据模型
│   │   ├── dto/             # 数据传输对象
│   │   ├── entity/          # 实体类
│   │   ├── enums/           # 枚举类
│   │   └── vo/              # 视图对象
│   ├── service/             # 业务逻辑层
│   ├── utils/               # 工具类
│   └── ZhishiPictureApplication.java
├── src/main/resources/
│   ├── biz/                 # 业务配置
│   ├── mapper/              # MyBatis XML
│   └── application.yml      # 主配置
└── pom.xml
```

## 快速开始

### 环境要求

- JDK 11+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 数据库初始化

1. 创建数据库：
```sql
CREATE DATABASE zhishi_picture DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行表结构初始化（见项目文档或联系管理员获取 SQL 文件）

### 配置文件

编辑 `src/main/resources/application-local.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/zhishi_picture
    username: your_username
    password: your_password
  redis:
    host: 127.0.0.1
    port: 6379
```

### 第三方服务配置

在 `application.yml` 中配置以下服务：

- **腾讯云 COS**: 对象存储服务
- **阿里云 AI**: 智能扩图服务

### 运行项目

```bash
# 编译
mvn clean compile

# 运行
mvn spring-boot:run

# 或打包后运行
mvn clean package
java -jar target/zhishi-picture-backend-0.0.1-SNAPSHOT.jar
```

### 访问接口文档

启动后访问：http://localhost:8123/api/doc.html

## API 概览

| 模块 | 接口路径 | 说明 |
|------|----------|------|
| 用户 | /api/user/** | 用户注册、登录、管理 |
| 图片 | /api/picture/** | 图片上传、编辑、搜索 |
| 空间 | /api/space/** | 空间创建、管理 |
| 空间成员 | /api/spaceUser/** | 空间成员权限管理 |
| 空间分析 | /api/spaceAnalyze/** | 空间数据分析 |
| 文件 | /api/file/** | 文件上传管理 |

## 权限说明

系统采用 Sa-Token 进行权限控制，空间内权限分为：

- **浏览者**: 仅可查看图片
- **编辑者**: 可上传、编辑图片
- **管理员**: 拥有空间所有权限

## 分库分表说明

系统支持按空间ID对图片表进行动态分表，配置在 `application.yml` 中：

```yaml
shardingsphere:
  rules:
    sharding:
      tables:
        picture:
          actual-data-nodes: zhishi_picture.picture
          table-strategy:
            standard:
              sharding-column: spaceId
              sharding-algorithm-name: picture_sharding_algorithm
```

## 开发规范

- 统一返回格式：`BaseResponse<T>`
- 统一异常处理：`GlobalExceptionHandler`
- 统一错误码：`ErrorCode`
- 使用 Lombok 简化代码
- 使用 MyBatis-Plus 进行数据库操作

## 许可证

[LICENSE](LICENSE)

