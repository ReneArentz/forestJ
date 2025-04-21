<p align="center">
  <a href="https://forestany.net/" target="_blank">
    <img alt="forestJ" src="https://forestany.net/pngs/fjava-logo.png" width="400">
  </a>
</p>

This custom-built [forestJ Framework](https://forestany.net/fjava.php) in Java, developed in Eclipse, is designed to streamline the development of robust applications by providing a comprehensive suite of tools and interfaces.

It offers seamless integration with console applications, efficient file handling solutions, and database management capabilities. Additionally, the framework supports both FTP and SFTP protocols for secure file transfers, and includes advanced features for socket programming, enabling smooth communication across networks. By combining these functionalities, the framework simplifies complex tasks, threading, enhancing developer productivity and application performance.

Following **database systems** are supported by forestJ:

* MariaDB/MySQL
* SQLite3
* MSSQL
* OracleDB
* PostgreSQL
* MongoDB

This framework is designed to be in sync with the corresponding *forestNET framework* in C#, ensuring both platforms offer the same functionality and capabilities. This alignment enables seamless interoperability between Java and .NET applications, allowing for smooth integration and consistent performance across diverse environments.

forestJ framework will be released under the **GPLv3 license** and the **MIT license**. Thus it is freely possible to use forestJ in other projects - projects with free software or in commercial projects.

## Releases

### 1.0.13 (stable) + fJ-ai-lib 1.0.0 (stable) + fJ-net-lib 1.0.0 (stable)
Added functionality to create and use neural networks for ai purposes. Added functionality for simple web requests over http(s). *04/2025*

### 1.0.12 (stable) + fJ-sql-lib 1.0.0 (stable) + fJ-sql-pool 1.0.0 (stable) + fJ-sql-mariadb 1.0.0 (stable) + fJ-sql-mssql 1.0.0 (stable) + fJ-sql-nosqlmdb 1.0.0 (stable) + fJ-sql-oracle 1.0.0 (stable) + fJ-sql-pgsql 1.0.0 (stable) + fJ-sql-sqlite 1.0.0 (stable)
Enabled integration of database management capabilities. *04/2025*

### 1.0.11 (stable)
Added XML file parser. *04/2025*

### 1.0.10 (stable)
Added JSON file parser. *03/2025*

### 1.0.9 (stable)
Added YAML file parser. *03/2025*

### 1.0.8 (stable)
Additional functionalities: ZIP compression, CSV file parser. *03/2025*

### 1.0.7 (stable)
Added support for flat files or fixed record length files. Automatically detecting records, group headers or footers as stacks of data. *02/2025*

### 1.0.6 (stable)
Additional core functionalities: Timer, State machine, File system watcher, Dijkstra shortest path algorithm. *01/2025*

### 1.0.5 (stable)
Implementation of console progress bar functionality and symmetric cryptography AES/GCM methods. *12/2024*

### 1.0.4 (stable)
Sorts class as collection of static methods to sort dynamic lists and dynamic key-value maps. Also possibility to get sort progress with delegate implementation. *12/2024*

### 1.0.3 (stable)
Added functionality for currency handling, date interval and memory observation. *12/2024*

### 1.0.2 (stable)
Added logging functionaly within global singleton class of forestJ library. *11/2024*

### 1.0.1 (stable)
Added file handling library functions. *11/2024*

### 1.0.0 (stable)
First release of the forestJ Framework 1.0.0 (stable). Provision of foundation files(Helper) + console application library functions. *10/2024*

## Tests

* **Windows**
	* Microsoft Windows 11 Pro - OS Version: 10.0.26100 N/A Build 26100
	* Eclipse 2024-03 (4.31.0)
  * OpenJDK Runtime Environment Corretto-21.0.5.11.1 (build 21.0.5+11-LTS)
  * Apache Maven 3.9.9

* **Database**

  * Linux - Linux version 6.1.0-33-amd64 (debian-kernel@lists.debian.org) (gcc-12 (Debian 12.2.0-14) 12.2.0, GNU ld (GNU Binutils for Debian) 2.40) #1 SMP PREEMPT_DYNAMIC Debian 6.1.133-1 (2025-04-10)
    * Mariadb
      * 10.11.11-MariaDB-0+deb12u1
    * MSSQL
      * Microsoft SQL Server 2022 (RTM-CU16) (KB5048033) - 16.0.4165.4 (X64)
    * Oracle
      * Oracle Database 23ai Free Release 23.0.0.0.0 - Version 23.7.0.25.01
    * PGSQL
      * PostgreSQL 15.12 (Debian 15.12-0+deb12u2) on x86_64-pc-linux-gnu, compiled by gcc (Debian 12.2.0-14) 12.2.0, 64-bit
    * MongoDB
      * 7.0.19
    * SQLite
      * 3.48.0
  * Windows - Microsoft Windows 11 Enterprise Evaluation - 10.0.22621 N/A Build 22621
    * Mariadb
      * 11.7.2-MariaDB
    * MSSQL
      * Microsoft SQL Server 2022 (RTM) - 16.0.1000.6 (X64)
    * Oracle
      * Oracle Database 21c Express Edition Release 21.0.0.0.0 - Version 21.3.0.0.0
    * PGSQL
      * PostgreSQL 17.4 on x86_64-windows, compiled by msvc-19.42.34436, 64-bit
    * MongoDB
      * 8.0.8
    * SQLite
      * 3.48.0