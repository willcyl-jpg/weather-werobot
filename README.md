# Weather Application

本项目是一个天气信息服务应用，基于Java和Spring Boot开发。它支持天气数据的获取、处理和推送，适用于机器人集成和自动化任务。

## 项目结构
- `src/main/java/com/will/weather/`：核心Java代码，包括控制器、服务、中间件和数据表示层。
- `src/main/resources/`：资源文件，包括配置、静态文件和模板。
- `test/`：测试代码。
- `pom.xml`：Maven项目配置文件。

## 快速开始
1. **环境要求**：
   - JDK 8或以上
   - Maven 3.6+
2. **构建项目**：
   ```cmd
   mvnw.cmd clean package
   ```
3. **运行项目**：
   ```cmd
   mvnw.cmd spring-boot:run
   ```
4. **访问服务**：
   默认端口为8080，可通过`http://localhost:8080`访问。

## 主要功能
- 获取指定城市的天气信息
- 机器人消息推送
- 定时天气任务

## 配置说明
- `application.properties`：配置服务端口、API密钥等参数
- `China-City-List-latest.csv`：城市列表数据文件

## 贡献
欢迎提交Issue和Pull Request。

## License
MIT

