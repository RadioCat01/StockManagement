# Remote Deployment - Spring Boot WAR to External Apache Tomcat on Linux Servers.
---
## Prerequisites
1. Install Required Software
2. Configure Tomcat Manager Roles
3. Configure Maven Deploy Plugin in POM.xml
4. Set Up Maven Deployment Credentials
5. Deploy the Application
---
## 1. Install Required Software
- Java JDK
- Apache Maven
- Apache Tomcat (wget from the official site) {ex: cd /opt
sudo wget https://downloads.apache.org/tomcat/tomcat-9/v9.0.85/bin/apache-tomcat-9.0.85.tar.gz and extract}
---
## 2. Configure Tomcat Manager Roles
Edit conf/tomcat-users.xml and add a user with necessary manager roles. This allows Maven and the Tomcat Maven Plugin to authenticate and deploy remotely.
```
<?xml version="1.0" encoding="UTF-8"?>
<tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">
    <role rolename="manager-gui"/>
    <role rolename="manager-script"/>
    <role rolename="manager-jmx"/>
    <role rolename="manager-status"/>
    <user username="admin" password="password" roles="manager-gui,manager-script,manager-jmx,manager-status"/>
</tomcat-users>
```
---
## 3. Configure Maven Deploy Plugin
In the <build><plugins> section of pom.xml, add both the WAR and Tomcat Maven plugins:
```
<build>
  <plugins>
    <!-- Package as WAR -->
    <plugin>
      <artifactId>maven-war-plugin</artifactId>
      <version>3.4.0</version>
    </plugin>
    <!-- Tomcat Maven Plugin for remote deployment -->
    <plugin>
      <groupId>org.apache.tomcat.maven</groupId>
      <artifactId>tomcat7-maven-plugin</artifactId>
      <version>2.2</version>
      <configuration>
        <url>http://127.0.0.1:8080/manager/text</url>
        <server>TomcatServer</server>
        <path>/stock</path> <!-- Context path -->
      </configuration>
    </plugin>
  </plugins>
</build>
```
Explanation:
  - tomcat7-maven-plugin works with Tomcat 7, 8, and 9.
  - URL points to your Tomcat Manager endpoint.
  - path sets your app URL path, e.g., http://<server>:8080/stock.
---
## 4.Set Up Maven Deployment Credentials
Edit (or create) ~/.m2/settings.xml on the machine running Maven and add your Tomcat server credentials:
```
<servers>
  <server>
    <id>TomcatServer</id>
    <username>admin</username>
    <password>password</password>
  </server>
</servers>
```
Explanation:
   - The <id> must match <server> in your plugin configuration above.
   - Do not commit this file—it holds sensitive data.
---
## 6. Deploy the Application
From your project's root directory, run:
bash
```
mvn clean package
mvn tomcat7:deploy
```

# Manual Deployment - Spring Boot WAR to External Apache Tomcat on Linux Servers.



