# c195SchedulerApp
Scheduling Application
C195 Performance Assessment
This application is a scheduling application using MySQL for data persistence. It allows a user to create and modify customers and appointments, and display notifications of upcoming appointments. It also maintained an access control log.

The goal of the exercise was to demonstrate proficiency in database connections, lambda functions, exception handing and assertions, localization, and logging.

IntelliJ and SceneBuilder are the tools I used.

Usage
This project required a MySQL connector. I used mysql-connector-java-5.1.41 from Oracle.

  You will also need to create a database.properties file in your root directory.
  
  dbdriver=com.mysql.jdbc.Driver
  
  dburl=jdbc:mysql://HOST/DATABASE
  
  dbuser=test
  
  dbpassword=test