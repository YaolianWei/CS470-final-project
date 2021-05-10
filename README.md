# CS570-final-project
Exploring Spark
## 1 Introduction
Apache Spark™ is a unified analytics engine for large-scale data processing.  
Spark has three main features:  
First, the advanced API removes the focus on the cluster itself, allowing Spark application developers can focus on the calculation itself that the application needs to do.  
Second, Spark is fast and supports interactive computing and complex algorithms.  
Finally, Spark is a general-purpose engine that can be used to perform a variety of computations, including SQL queries, text processing, machine learning, etc. Before Spark came along, we typically had to learn a variety of engines to handle these requirements separately.   

## 2 Installation
### 2.1 JDK Installation
Download link: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html  
Envionment variables：  
Establish JAVA_HOME：C:\Program Files\Java\jdk1.8.0_291  
Establish CLASSPATH：.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar  
Add Path：%JAVA_HOME%\bin;  
cmd input: `java -version`  
![jdk](https://user-images.githubusercontent.com/78562542/115888264-4e86e100-a485-11eb-9507-777b0d232b41.png)

### 2.2 Spark Installation
Download link: http://spark.apache.org/downloads.html  
cmd input: `spark-shell`   
![spark](https://user-images.githubusercontent.com/78562542/115888944-0e742e00-a486-11eb-968a-3af433b1f9d8.png)

### 2.3 Scala Installation
Download link: https://www.scala-lang.org/download/2.11.12.html  
cmd input: `scala`  
![scala](https://user-images.githubusercontent.com/78562542/115888974-192ec300-a486-11eb-9513-ae9bfd441c3c.png)

After IDEA installation is complete, install the Scala plugin:  
start IntelJ, Click on the Configuration -> Plugins, or file->setting->Plugins; search for Scala and install the plugin.  

## 3 Hello World
Study link: https://docs.scala-lang.org/getting-started/intellij-track/getting-started-with-scala-in-intellij.html

## 4 An example

