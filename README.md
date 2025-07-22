# Deploying Spring Boot WAR to External Apache Tomcat on Linux Servers 01

Steps for deploying a **Spring Boot WAR** application to an externally installed **Apache Tomcat** server on a Linux machine/ Server.

---

## Prerequisites

- [1. Install Required Software](#1-install-required-software)  
- [2. Configure Tomcat Manager Roles](#2-configure-tomcat-manager-roles)  
- [3. Prepare Spring Boot for WAR Packaging](#3-prepare-spring-boot-for-war-packaging)  
- [4. Configure Maven Deploy Plugin](#4-configure-maven-deploy-plugin)  
- [5. Set Up Maven Deployment Credentials](#5-set-up-maven-deployment-credentials)  
- [6. Deploy the Application](#6-deploy-the-application)  
- [Tips & Best Practices](#tips--best-practices)  

---


## 1. Install Required Software on the server

- Relevent Java JDK
- Maven
- Apache Tomcat (from the official site) {ex: cd /opt
sudo wget https://downloads.apache.org/tomcat/tomcat-9/v9.0.85/bin/apache-tomcat-9.0.85.tar.gz and extract}
