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
 
**Solution:** augment data flow model with “resilient distributed dataset” (RDD).

#### 1.1.3 Spark Ecosystem and Architecture
##### 1.1.3.1 Spark Ecosystem
- **Spark Core**  
Spark Core is the base engine for large-scale parallel and distributed data processing. Further, additional libraries which are built on the top of the core allows diverse workloads for streaming, SQL, and machine learning. It is responsible for memory management and fault recovery, scheduling, distributing and monitoring jobs on a cluster & interacting with storage systems.
- **Spark Streaming**  
Spark Streaming is the component of Spark which is used to process real-time streaming data. Thus, it is a useful addition to the core Spark API. It enables high-throughput and fault-tolerant stream processing of live data streams.
- **Spark SQL**  
Spark SQL is a new module in Spark which integrates relational processing with Spark’s functional programming API. It supports querying data either via SQL or via the Hive Query Language. For those of you familiar with RDBMS, Spark SQL will be an easy transition from your earlier tools where you can extend the boundaries of traditional relational data processing.
- **GraphX**  
GraphX is the Spark API for graphs and graph-parallel computation. Thus, it extends the Spark RDD with a Resilient Distributed Property Graph. At a high-level, GraphX extends the Spark RDD abstraction by introducing the Resilient Distributed Property Graph (a directed multigraph with properties attached to each vertex and edge).
- **MLlib (Machine Learning)**  
MLlib stands for Machine Learning Library. Spark MLlib is used to perform machine learning in Apache Spark.

##### 1.1.3.2 Spark Architecture
![Spark Architecture](https://user-images.githubusercontent.com/78562542/117964929-cd6c8c80-b354-11eb-872b-b121bb0766c1.png)  


#### 1.1.4 RDD
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

### 1.3 Applications
**An Example - Pregel:** Graph processing framework from Google that implements Bulk Synchronous Parallel model.
- Vertices in the graph have state
- At each superstep, each node can update its state and send messages to nodes in future step

**Pregel in Spark:**
- Separate RDDs for immutable graph state and for vertex states and messages at each iteration
- Use groupByKey to perform each step
- Cache the resulting vertex and message RDDs
- Optimization: co-partition input graph and vertex state RDDs to reduce communication

**Other Spark Applications:**  
• Twitter spam classification  
• EM alg. for traffic prediction (Mobile Millennium)  
• K-means clustering  
• Alternating Least Squares matrix factorization  
• In-memory OLAP aggregation on Hive data  

## 2 Windoews Installation
### 2.1 Base Installation
Before installing the tool, we need to configure the Java environment. And My Java version is that:  
cmd input: `java -version`  
![jdk](https://user-images.githubusercontent.com/78562542/115888264-4e86e100-a485-11eb-9507-777b0d232b41.png)  
I will use IDEA as the development environment.  
After IDEA installation is completed, install the Scala plugin:  
start IntelJ, Click on the Configuration -> Plugins, or file->setting->Plugins; search for Scala and install the plugin. 

### 2.2 Spark Installation
Download link: [spark](http://spark.apache.org/downloads.html)  
After downloading, unzip it to the specified directory.   
After decompression, you can almost run it under the cmd command line. But at this time, every time you run spark-shell (spark's command line interactive window), you need to first `cd` to the Spark installation directory, which is troublesome. Therefore, you can add the Spark bin directory to the system variables PATH. For example, the bin directory path of Spark here is `D:\Spark\bin`, so just add this path name to the PATH of the system variables.  
I can set the envionment variables like this：  
Establish SPARK_HOME：`D:\Spark`   
Add to PATH：`%SPARK_HOME%\bin`   
Test whether the installation is successful:  
cmd input: `spark-shell`   
![spark](https://user-images.githubusercontent.com/78562542/115888944-0e742e00-a486-11eb-968a-3af433b1f9d8.png)

### 2.3 Scala Installation
Download link: [scala-2.11.12](https://www.scala-lang.org/download/2.11.12.html)  
The above link mentions a variety of Scala installation methods. The easier one is to install the scala SDK through the intelj IDEA with the scala plugin installed (note the distinction between plugins and SDK): File => New => Project, select Scala, enter the project name if it is the first time to create a Scala project, there will be a `Create` button of the `Scala SDK`, and then select the required version to install.  
And I use the installation package installation method.  
**Note:** Each version of Spark needs to correspond to the corresponding Scala version.  
After downloading the Scala msi file, you can double-click to install it. After the installation is successful, the Scala bin directory will be added to the PATH system variable by default (if not, similar to the Spark installation steps, add the bin directory path under the Scala installation directory to the system variable PATH), in order to verify whether the installation is successful, open a new cmd window, type `scala` and press Enter. If you can enter the interactive command environment of Scala normally, the installation is successful. As shown below:  
![scala](https://user-images.githubusercontent.com/78562542/115888974-192ec300-a486-11eb-9513-ae9bfd441c3c.png)

### 2.4 Hadoop Installation
Download link: [hadoop-2.7.7](https://archive.apache.org/dist/hadoop/common/hadoop-2.7.7/)  
Download and unzip to the specified directory, and then set HADOOP_HOME as the decompression directory of Hadoop in the environment variable section. And then set the bin directory under this directory to the PATH of the system variable.  
For example I set the envionment variables like this：  
Establish SPARK_HOME：`D:\hadoop-2.7.7`   
Add to PATH：`%HADOOP_HOME%\bin`

## 3 Hello World
### 3.1 Hello World Test
Study link: https://docs.scala-lang.org/getting-started/intellij-track/getting-started-with-scala-in-intellij.html

### 3.2 Pi Test
#### 3.2.1 Java Spark Pi
I tried to execute the JavaSparkPi code in local stand-alone mode. In this mode, all Spark processes run in the same JVM, and parallel processing is done by multi-threading. By default, this code will enable the same number of threads as the local system's CPU cores.  
The output is shown in the figure:
![JavaSparkPi](https://user-images.githubusercontent.com/78562542/117886546-f3a51480-b2e1-11eb-873a-e8d9356e03e6.png)
We can set the level of parallelism in local mode, specify a master variables in the format local[N]. The N in the above argument indicates the number of threads to use.  
The following figure shows that I set * in local[]:
![setThreads](https://user-images.githubusercontent.com/78562542/117887225-f5bba300-b2e2-11eb-9132-58c66fd92270.png)

#### 3.2.2 Scala Spark Pi
![Pi](https://user-images.githubusercontent.com/78562542/117887787-d07b6480-b2e3-11eb-8c50-4dc66e359cd2.png)

## 4 An example-WordCount


### References
[1]Jorge L. Reyes-Ortiz, Luca Oneto, Davide Anguita,Big Data Analytics in the Cloud: Spark on Hadoop vs MPI/OpenMP on Beowulf,Procedia Computer Science,
Volume 53,2015,Pages 121-130,ISSN 1877-0509,https://doi.org/10.1016/j.procs.2015.07.286.
