# Online Exam - 在线考试系统

[![Java Version](https.svg?alt=java&color=blue)](https://img.shields.io/badge/Java-1.8-blue) [![Spring](https://img.shields.io/badge/Spring-4.3.7-brightgreen)](https://spring.io/) [![MySQL](https://img.shields.io/badge/MySQL-5.7-orange)](https://www.mysql.com/)

一个基于 SSM (Spring, Spring MVC, MyBatis) 框架开发的现代化在线考试系统。系统分为功能强大的后台管理端和简洁易用的前台学生端。

## ✨ 项目特色

- **前后端分离**: 后端专注业务逻辑，前端专注用户体验。
- **MVC架构**: 经典三层架构，代码结构清晰，易于维护和扩展。
- **权限控制**: 基于角色的权限管理，保障系统安全。
- **动态组卷**: 支持在学生开始考试时动态、随机地从题库生成试卷。
- **实时保存**: 考试过程中答案实时异步保存，防止数据丢失。
- **自动阅卷**: 客观题自动批改，提交后即时获得成绩。

## 📸 系统截图

| 后台登录 | 后台主页 |
| :---: | :---: |
| ![后台登录](https://github.com/CeciliaElf/OnlineExam/blob/cb25a3efe8fffbeff9fd3da1d64573725be4e152/ScreenShots/00.png) | ![后台主页](https://github.com/CeciliaElf/OnlineExam/blob/cb25a3efe8fffbeff9fd3da1d64573725be4e152/ScreenShots/01.png) |
| **试题管理** | **学生管理** |
| ![试题管理](https://github.com/CeciliaElf/OnlineExam/blob/cb25a3efe8fffbeff9fd3da1d64573725be4e152/ScreenShots/02.png) | ![学生管理](https://github.com/CeciliaElf/OnlineExam/blob/cb25a3efe8fffbeff9fd3da1d64573725be4e152/ScreenShots/03.png) |
| **学生端登录** | **学生考试列表** |
| ![学生端登录](https://github.com/CeciliaElf/OnlineExam/blob/cb25a3efe8fffbeff9fd3da1d64573725be4e152/ScreenShots/06.png) | ![学生考试列表](https://github.com/CeciliaElf/OnlineExam/blob/cb25a3efe8fffbeff9fd3da1d64573725be4e152/ScreenShots/07.png) |
| **在线考试** | **历史成绩** |
| ![在线考试](https://github.com/CeciliaElf/OnlineExam/blob/cb25a3efe8fffbeff9fd3da1d64573725be4e152/ScreenShots/08.png) | ![历史成绩](https://github.com/CeciliaElf/OnlineExam/blob/cb25a3efe8fffbeff9fd3da1d64573725be4e152/ScreenShots/09.png) |


## 🚀 技术栈

- **核心框架**: Spring Framework
- **Web框架**: Spring MVC
- **ORM框架**: MyBatis
- **数据库**: MySQL / MariaDB
- **前端视图**: JSP, JSTL
- **前端库**: jQuery, EasyUI (后台), H-UI (前台)
- **Web服务器**: Tomcat
- **构建工具**: Maven
- **数据库连接池**: C3P0

## 🛠️ 安装与部署

请遵循以下步骤在您的本地环境或服务器上部署本系统。

### 1. 环境准备
-   JDK 1.8
-   Maven 3.5+
-   Tomcat 8.5+
-   MySQL 5.7+ 或 MariaDB

### 2. 克隆代码
```bash
git clone https://github.com/CeciliaElf/OnlineExam.git
cd OnlineExam
```

### 3. 数据库设置
1.  登录您的数据库，创建一个新数据库。
    ```sql
    CREATE DATABASE online_exam DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    -- 切换到该数据库
    USE online_exam;
    ```
2.  **创建数据表**: 在您的数据库中执行以下SQL语句来创建项目所需的所有数据表。这些语句已经包含了外键和级联关系。

    ```sql
    -- ----------------------------
    -- 1. 学科表
    -- ----------------------------
    CREATE TABLE `subject` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `name` varchar(128) NOT NULL COMMENT '学科名称',
      `remark` varchar(256) DEFAULT NULL COMMENT '备注',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 2. 学生表
    -- ----------------------------
    CREATE TABLE `student` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `subjectId` bigint(20) DEFAULT NULL COMMENT '所属学科ID',
      `name` varchar(32) NOT NULL COMMENT '登录名',
      `password` varchar(32) NOT NULL COMMENT '登录密码',
      `trueName` varchar(32) DEFAULT NULL COMMENT '真实姓名',
      `tel` varchar(16) DEFAULT NULL COMMENT '手机号',
      `createTime` datetime NOT NULL COMMENT '注册时间',
      PRIMARY KEY (`id`),
      UNIQUE KEY `name_UNIQUE` (`name`),
      KEY `FK_student_subject` (`subjectId`),
      CONSTRAINT `FK_student_subject` FOREIGN KEY (`subjectId`) REFERENCES `subject` (`id`) ON DELETE SET NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 3. 试题表
    -- ----------------------------
    CREATE TABLE `question` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `subjectId` bigint(20) NOT NULL COMMENT '所属学科ID',
      `questionType` int(4) NOT NULL COMMENT '类型(0:单选, 1:多选, 2:判断)',
      `title` text NOT NULL COMMENT '题干',
      `score` int(4) NOT NULL COMMENT '分值',
      `attrA` varchar(255) DEFAULT NULL,
      `attrB` varchar(255) DEFAULT NULL,
      `attrC` varchar(255) DEFAULT NULL,
      `attrD` varchar(255) DEFAULT NULL,
      `answer` varchar(8) NOT NULL COMMENT '正确答案',
      `createTime` datetime NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK_question_subject` (`subjectId`),
      CONSTRAINT `FK_question_subject` FOREIGN KEY (`subjectId`) REFERENCES `subject` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 4. 考试表
    -- ----------------------------
    CREATE TABLE `exam` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `name` varchar(128) NOT NULL COMMENT '考试名称',
      `subjectId` bigint(20) NOT NULL COMMENT '所属学科ID',
      `startTime` datetime NOT NULL,
      `endTime` datetime NOT NULL,
      `availableTime` int(5) NOT NULL COMMENT '考试时长(分钟)',
      `questionNum` int(5) NOT NULL COMMENT '题目总数',
      `totalScore` int(5) NOT NULL COMMENT '总分',
      `passScore` int(5) NOT NULL COMMENT '及格线',
      `singleQuestionNum` int(5) NOT NULL DEFAULT 0,
      `mutiQuestionNum` int(5) NOT NULL DEFAULT 0,
      `chargeQuestionNum` int(5) NOT NULL DEFAULT 0,
      `paperNum` int(5) NOT NULL DEFAULT 0 COMMENT '已生成试卷数',
      `examedNum` int(5) NOT NULL DEFAULT 0 COMMENT '已考人数',
      `passNum` int(5) NOT NULL DEFAULT 0 COMMENT '及格人数',
      `createTime` datetime NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK_exam_subject` (`subjectId`),
      CONSTRAINT `FK_exam_subject` FOREIGN KEY (`subjectId`) REFERENCES `subject` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 5. 试卷表
    -- ----------------------------
    CREATE TABLE `exam_paper` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `examId` bigint(20) NOT NULL COMMENT '考试ID',
      `studentId` bigint(20) NOT NULL COMMENT '学生ID',
      `status` int(2) NOT NULL DEFAULT 0 COMMENT '状态(0:未考, 1:已考)',
      `totalScore` int(5) NOT NULL COMMENT '总分',
      `score` int(5) DEFAULT NULL COMMENT '得分',
      `startExamTime` datetime DEFAULT NULL,
      `endExamTime` datetime DEFAULT NULL,
      `useTime` int(5) DEFAULT NULL COMMENT '用时(分钟)',
      `createTime` datetime NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK_exampaper_exam` (`examId`),
      KEY `FK_exampaper_student` (`studentId`),
      CONSTRAINT `FK_exampaper_exam` FOREIGN KEY (`examId`) REFERENCES `exam` (`id`) ON DELETE CASCADE,
      CONSTRAINT `FK_exampaper_student` FOREIGN KEY (`studentId`) REFERENCES `student` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 6. 试卷答题表
    -- ----------------------------
    CREATE TABLE `exam_paper_answer` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `examPaperId` bigint(20) NOT NULL COMMENT '试卷ID',
      `questionId` bigint(20) DEFAULT NULL COMMENT '题目ID',
      `answer` varchar(8) DEFAULT NULL COMMENT '学生答案',
      `isCorrect` int(2) DEFAULT 0,
      PRIMARY KEY (`id`),
      KEY `FK_answer_exampaper` (`examPaperId`),
      KEY `FK_answer_question` (`questionId`),
      CONSTRAINT `FK_answer_exampaper` FOREIGN KEY (`examPaperId`) REFERENCES `exam_paper` (`id`) ON DELETE CASCADE,
      CONSTRAINT `FK_answer_question` FOREIGN KEY (`questionId`) REFERENCES `question` (`id`) ON DELETE SET NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 7. 角色表
    -- ----------------------------
    CREATE TABLE `role` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `name` varchar(32) NOT NULL,
      `remark` varchar(128) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 8. 管理员表
    -- ----------------------------
    CREATE TABLE `user` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `roleId` bigint(20) DEFAULT NULL,
      `name` varchar(32) NOT NULL,
      `password` varchar(32) NOT NULL,
      `trueName` varchar(32) DEFAULT NULL,
      `tel` varchar(16) DEFAULT NULL,
      `createTime` datetime NOT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `name_UNIQUE` (`name`),
      KEY `FK_user_role` (`roleId`),
      CONSTRAINT `FK_user_role` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE SET NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 9. 菜单表
    -- ----------------------------
    CREATE TABLE `menu` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `parentId` bigint(20) NOT NULL DEFAULT 0,
      `name` varchar(64) NOT NULL,
      `url` varchar(256) DEFAULT NULL,
      `icon` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    -- ----------------------------
    -- 10. 权限表
    -- ----------------------------
    CREATE TABLE `authority` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `roleId` bigint(20) NOT NULL,
      `menuId` bigint(20) NOT NULL,
      PRIMARY KEY (`id`),
      KEY `FK_authority_role` (`roleId`),
      KEY `FK_authority_menu` (`menuId`),
      CONSTRAINT `FK_authority_role` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE CASCADE,
      CONSTRAINT `FK_authority_menu` FOREIGN KEY (`menuId`) REFERENCES `menu` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    ```

### 4. 项目配置
打开 `src/config/db.properties` 文件，修改为您的数据库连接信息。
```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/online_exam?useUnicode=true&characterEncoding=utf8
jdbc.username=your_username
jdbc.password=your_password
```

### 5. 编译与打包
在项目根目录运行Maven命令：
```bash
mvn clean package
```
这将在 `target/` 目录下生成 `OnlineExam.war` 文件。

### 6. 部署
将 `OnlineExam.war` 文件复制到 Tomcat 的 `webapps/` 目录下。Tomcat 将自动解压并部署此应用。

## 📌 使用说明

-   **后台管理地址**: `http://localhost:8080/OnlineExam/system/login`
    -   **默认账号**: `admin`
    -   **默认密码**: `admin`
-   **前台学生地址**: `http://localhost:8080/OnlineExam/home/login`
    -   学生可自行注册账号登录。

## 🤝 贡献者

本项目由以下成员共同完成：

| 成员 | 职位 | 贡献详情 | 贡献占比 |
| :--- | :--- | :--- | :--- |
| **张雨晨** | 组长 | 负责项目的整体架构设计、核心功能的全栈开发、数据库设计以及相关技术文档的主要编写工作。 | 65% |
| **丁鑫** | 组员 | 参与了部分后端接口的开发，协助完成了数据库的数据初始化工作，并负责收集和整理部分测试用例。 | 17.5% |
| **刘祖睿** | 组员 | 参与了部分前端页面的开发与调试，协助进行了系统的测试工作，并对UI细节提出了改进建议。 | 17.5% |

## 📄 开源许可

本项目采用 [MIT License](LICENSE) 开源许可。 