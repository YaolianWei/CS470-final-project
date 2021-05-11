# CS570-final-project
Exploring Spark
## 1 Introduction
### 1.1 Describe Spark
**Apache Spark™** is a unified analytics engine for large-scale data processing.It provides high-level APIs in Java, Scala, Python and R, and an optimized engine that supports general execution graphs. It also supports a rich set of higher-level tools including Spark SQL for SQL and structured data processing, MLlib for machine learning, GraphX for graph processing, and Structured Streaming for incremental computation and stream processing.  
Spark has three main features:  
First, the advanced API removes the focus on the cluster itself, allowing Spark application developers can focus on the calculation itself that the application needs to do.  
Second, Spark is fast and supports interactive computing and complex algorithms.  
Finally, Spark is a general-purpose engine that can be used to perform a variety of computations, including SQL queries, text processing, machine learning, etc. Before Spark came along, we typically had to learn a variety of engines to handle these requirements separately. 
References Link: [Spark Documentation](https://spark.apache.org/docs/latest/)  

#### 1.1.1 Motivation for Spark: 
Current popular programming models for clusters transform data flowing from stable storage to stable storage.  
e.g., MapReuce:
![MapReduce](https://user-images.githubusercontent.com/78562542/117876353-e08c4780-b2d5-11eb-8c4d-bfa84015371a.png)
**Benefits of data flow:** runtime can decide where to run tasks and can automatically recover from failures.  
Acyclic data flow is a powerful abstraction, but is not efficient for applications that repeatedly reuse a working set of data:  
- Iterative algorithms (many in machine learning)
- Interactive data mining tools (R, Excel, Python)  

Spark makes working sets a first-class concept to efficiently support these apps.  

#### 1.1.2 Spark Goal:
- Provide distributed memory abstractions for clusters to support apps with working sets.  
- Retain the attractive properties of MapReduce:
  * Fault tolerance (for crashes & stragglers)
  * Data locality
  * Scalability
 
**Solution:** augment data flow model with “resilient distributed dataset” (RDD)

#### 1.1.3 RDD
At a high level, every Spark application consists of a _driver program_ that runs the user’s main function and executes various _parallel operations_ on a cluster. The main abstraction Spark provides is a _resilient distributed dataset (RDD)_, which is a collection of elements partitioned across the nodes of the cluster that can be operated on in parallel. RDDs are created by starting with a file in the Hadoop file system (or any other Hadoop-supported file system), or an existing Scala collection in the driver program, and transforming it. Users may also ask Spark to _persist_ an RDD in memory, allowing it to be reused efficiently across parallel operations. Finally, RDDs automatically recover from node failures.  
A second abstraction in Spark is shared _variables_ that can be used in parallel operations. By default, when Spark runs a function in parallel as a set of tasks on different nodes, it ships a copy of each variable used in the function to each task. Sometimes, a variable needs to be shared across tasks, or between tasks and the driver program. Spark supports two types of shared variables: _broadcast variables_, which can be used to cache a value in memory on all nodes, and _accumulators_, which are variables that are only “added” to, such as counters and sums.  
**Resilient distributed dataset (RDD):**
- An RDD is an immutable, partitioned, logical collection of records
  * Need not be materialized, but rather contains information to rebuild a dataset from stable storage
- Partitioning can be based on a key in each record (using hash or range partitioning)
- Built using bulk transformations on other RDDs
- Can be cached for future reuse

Spark revolves around the concept of a _resilient distributed dataset (RDD)_, which is a fault-tolerant collection of elements that can be operated on in parallel. There are two ways to create RDDs: parallelizing an existing collection in your driver program, or referencing a dataset in an external storage system, such as a shared filesystem, HDFS, HBase, or any data source offering a Hadoop InputFormat.

### 1.2 Compare Spark to MPI and OpenMP
Message Passing Interface(MPI) is a language-independent communications protocol for parallel computing where point-to-point and collective communication are supported. MPI goalsare high performance, scalability, and portability. MPI is currently the dominant model used inhigh-performance computing and is ade factocommunication standard that provides portability among parallel programs running on distributed memory systems. However, the standarddoes not currently support fault tolerance since it mainly addresses High-Performance Com-puting(HPC) problems. Another MPI drawback is that it is not suitable for small grain level ofparallelism, for example, to exploit the parallelism of multi-core platforms for shared memorymultiprocessing.   
OpenMP, on the other hand, is an Application Programming Interface(API) that supports multi-platform shared memory multiprocessing programming on mostprocessor architectures and operating systems. OpenMP is becoming the standard for sharedmemory parallel programming for its high performance, but unfortunately, it is not suitablefor distributed memory systems. The idea of extending this API to cope with this issue isnow a growing field of research. OpenMP’s user-friendly interface allows to easily parallelizecomplex algorithms. The same thing cannot be said about MPI since the code must be heav-ily re-engineered in order to obtain relevant performance improvements.  
From references paper[1], we can know some conclusions:
- From a computational point of view, the MPI/OpenMP implementation is much more powerful than the Spark on Hadoop alternative in terms of speed.
- MPI/OpenMP scales better than Spark. This is mainly to its low level programming languageand reduced overhead (e.g. no fault handling such as in Spark). The speedup of MPI/OpenMP over Spark is due to the dataset size with respect to the cluster size. Witha larger dataset, time differences between the two implementations would be smaller. This is explained due to an increase of the effective computation time (e.g. classification) with respect to the overhead of the technologies.
- For what concerns to data I/O management, MPI/OpenMP and Spark are much closer in terms of disk access time although MPI/OpenMP is still faster. Moreover, Spark on Hadoop is better suited to exploit the GCP infrastructure since it is able to split the data inmultiple chunks so files can be read in parallel from many sectors. This is because in GCP,virtual disks can spread over multiple physical disks so to improve the I/O performance. Our MPI/OpenMP implementation does not currently exploit this possibility.  
**To sum up:**  

|Framework |Applicable Scene|
|-------- | -----|
| Hadoop |Hadoop is a distributed batch computing framework based on the distributed file system HDFS. It is suitable for large data volume, SPMD(single program multiple data) applications.|
| Spark |Spark is a parallel computing framework based on in-memory computing. It is suitable for applications that require multiple rounds of iterative computation.|
| MPI |MPI is a parallel computing framework based on message passing. It is suitable for parallel computing of various complex applications and supports MPMD(multi-program multi-data).|


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

## 4 An example-Pi
### 4.1 Java Spark Pi
I tried to execute the JavaSparkPi code in local stand-alone mode. In this mode, all Spark processes run in the same JVM, and parallel processing is done by multi-threading. By default, this code will enable the same number of threads as the local system's CPU cores.  
The output is shown in the figure:
![JavaSparkPi](https://user-images.githubusercontent.com/78562542/117886546-f3a51480-b2e1-11eb-873a-e8d9356e03e6.png)
We can set the level of parallelism in local mode, specify a master variables in the format local[N]. The N in the above argument indicates the number of threads to use.  
The following figure shows I set * in local[]:
![setThreads](https://user-images.githubusercontent.com/78562542/117887225-f5bba300-b2e2-11eb-9132-58c66fd92270.png)

### 4.2 Scala Spark Pi
![Pi](https://user-images.githubusercontent.com/78562542/117887787-d07b6480-b2e3-11eb-8c50-4dc66e359cd2.png)



### References
[1]Jorge L. Reyes-Ortiz, Luca Oneto, Davide Anguita,Big Data Analytics in the Cloud: Spark on Hadoop vs MPI/OpenMP on Beowulf,Procedia Computer Science,
Volume 53,2015,Pages 121-130,ISSN 1877-0509,https://doi.org/10.1016/j.procs.2015.07.286.

