https://stockmgt.duckdns.org/login

# Remote Deployment - Spring Boot WAR to External Apache Tomcat on Linux Servers.
---
## Prerequisites
1. Install Required Software on the Remote Server
2. Configure Tomcat Manager Roles on the Remote Server
4. Configure Maven Deploy Plugin in POM.xml in Local
5. Set Up Maven Deployment Credentials in Local
6. Deploy the Application
---
## 1. Install Required Software on the Remote Server
- Java JDK
- Apache Tomcat (wget from the official site) {ex: cd /opt
sudo wget https://downloads.apache.org/tomcat/tomcat-9/v9.0.85/bin/apache-tomcat-9.0.85.tar.gz and extract}
- Allow port 8080 open to internet (from security groups if using AWS EC2 instance)
---
## 2. Configure Tomcat Manager Roles on the Remote Server
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
Makesure the tomcat server.xml or Tomcat manager's web.xml in the external server does not block remote IPs.
In webapps/manager/META-INF/context.xml, comment out this block:
```
<!--
<Valve className="org.apache.catalina.valves.RemoteAddrValve"
       allow="127\.\d+\.\d+\.\d+|::1"/>
-->
```
---
## 3. Configure Maven Deploy Plugin in Local
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
        <url>http://<your-ec2-public-ip>:8080/manager/text</url>  <!-- This is the target ip -->
        <server>TomcatServer</server>   <!-- This is the ID used to fetch credentials -->
        <path>/stock</path>  <!-- Context path -->
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
## 4. Set Up Maven Deployment Credentials in Local
Edit (or create) ~/.m2/settings.xml on the machine running Maven and add your Tomcat server credentials:
```
<servers>
  <server>
    <id>TomcatServer</id>  <!-- Match to <server> in POM.xml of the application -->
    <username>admin</username>  <!-- Tomcat Credential user name -->
    <password>password</password>  <!-- Tomcat Credential password -->
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
> **🔁 This will look for the IP address and the port where Tomcat is working (which is mentioned in the POM.xml) and deploy the application using the tomcat credentials that are given in the local maven settings.xml file**


# CI/CD 
https://medium.com/@shubhangi.thakur4532/deploy-spring-boot-application-using-jenkins-with-github-integration-9d28c99ea168

dev.to/javafullstackdev/jenkins-and-spring-boot-a-comprehensive-guide-5f20
---

# Manual Deployment - Spring Boot WAR to External Apache Tomcat on Linux Servers.
Manually copying the WAR file to the Tomcat server's webapps directory. Tomcat then automatically detects the WAR file and deploys the application on restart or may need to start the application from manager page. 
