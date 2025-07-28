# STEM 教育管理系统

STEM是一个基于JavaFX的桌面教育管理应用程序，用于管理学生、家长、教师、活动和测验的综合平台<cite/>。

## 项目概述

该系统提供以下核心功能：
- **用户管理**：支持学生、家长和教师的身份验证和基于角色的访问控制
- **活动管理**：创建、安排和跟踪教育活动的参与情况
- **测验系统**：基于主题分类的测验管理和完成跟踪
- **家长-子女关系**：建立家长与子女的关联以便监督和管理
- **数据持久化**：使用Kryo序列化进行高效的系统数据存储

## 技术栈

| 组件 | 技术 | 版本 | 用途 |
|------|------|------|------|
| UI框架 | JavaFX | 21.0.1 | 桌面应用界面 |
| 序列化 | Kryo | 5.6.0 | 二进制数据持久化 |
| 图标库 | Kordamp Ikonli | 12.3.1 | UI图标系统 |
| 构建工具 | Maven | - | 项目管理和依赖管理 |
| Java版本 | Java | 17 | 运行环境 | [1](#0-0) 

## 项目结构

```
stem_app/
├── src/main/java/com/weijie/
│   ├── core/service/          # 核心服务层
│   ├── ui/controls/           # 自定义UI控件
│   ├── ui/FXTool/            # JavaFX工具类
│   └── ui/page/              # 页面组件
├── src/main/resources/
│   └── css/                  # 样式文件
└── src/test/java/            # 测试数据和测试类
```

## 快速开始

### 环境要求
- Java 17或更高版本
- Maven 3.6+

### 运行应用
```bash
# 克隆项目
git clone [repository-url]

# 进入项目目录
cd WIA1002

# 使用Maven运行
mvn javafx:run
``` [2](#0-1) 

### 构建项目
```bash
mvn clean compile
```

## 核心特性

### 用户认证系统
- 基于邮箱的登录验证
- 密码哈希加密
- 角色自动检测和UI路由
- 会话管理

### 自定义UI框架
系统使用基于`WJStage`的自定义JavaFX框架，提供：
- 透明窗口和自定义装饰
- 响应式布局
- 页面导航系统
- 集中化的资源管理 [3](#0-2) 

### 数据持久化
使用Kryo序列化实现高效的二进制数据存储：
- 用户数据自动序列化
- 活动和测验数据管理
- 应用关闭时自动保存
- 启动时自动加载数据

## 开发和测试

项目包含完整的测试数据生成系统，创建：
- 示例用户账户（学生、家长、教师）
- 家长-子女关系和朋友网络
- 教育活动样本数据
- STEM主题测验数据

## 贡献指南

1. Fork项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启Pull Request

## 许可证

本项目采用适当的开源许可证。详情请查看LICENSE文件。
