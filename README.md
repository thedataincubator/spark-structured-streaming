# Steps

* Install SBT: `brew install sbt`
* Copy this directory
* Remove the .git setup: `rm -rf .git`
* Change the project name and version in *build.sbt*
* Change the project name in *src/main/scala/myproject/Main.scala*
* Run an sbt console just to check if everything's ok: `sbt console`. It could take a few minutes the first time. It will will create the directories *project/target* and *target* (which are .gitignored). The result is a scala 2.10.4 console, with all the project dependencies loaded.
* Optionnaly intitialize a new git project: `git init`

# Import the project in IntelliJ IDEA

* Start IntelliJ IDEA. In the Welcome window, click on "Import Project"
* Enter the project path
* Choose the external model *SBT*
* Check
  * *Use auto-import*
  * *Download sources and docs*
* Choose a Project SDK with a version >= 1.7.
* Move the `Main` and `Schema` classes to your package name
* Right-click on the `Main` class and click `Run Main`

# Build and run your Spark job on a Spark cluster

We use [sbt-assembly](https://github.com/sbt/sbt-assembly) to bundle the application in a fat JAR, ready to be submitted to a Spark cluster. The JAR must not include the Spark components (spark-core, spark-sql, hadoop-client, etc) and their dependencies.

## To build the JAR:

* first exit Intellij IDEA if it's configured to track the changes to *build.sbt*
* edit *build.sbt*, to switch the *spark-** and *hadoop-** dependencies (see the comments inside *build.sbt*)
* run `sbt assembly`. The generated is in *target/scala-2.10/{projectname}-assembly-{version}.jar*

*TODO: try to remove the manual part of editing *build.sbt*.*

## To submit the JAR:

* scp the JAR on the spark master
* ssh on the spark master
* to prevent the job from stopping if you disconnect from the server, run: `screen`
* submit the JAR with the command: `~/spark/bin/spark-submit --master spark://ec2-w-x-y-z.eu-west-1.compute.amazonaws.com:7077 --class io.basilic.MySparkJob ~/MyProject-assembly-1.0.jar > /mnt/job.out &> /mnt/job.err`
* tail logs with: `tail -f /mnt/job.{out,err}`

# Treats

* Visualize the [graph of dependencies](https://github.com/jrudolph/sbt-dependency-graph) with the command `sbt dependency-graph`.

# Starting a Spark Cluster on EC2

TODO: write this paragraph

    TODO: By default, spark-ec2 runs with hadoop-client on 1.0.4.
      One can also run the cluster on 2.0.x with `--hadoop-major-version=2`,
      which is an alpha version. @see http://mvnrepository.com/artifact/org.apache.hadoop/hadoop-client
      spark-ec2 does not provide a way to use the stable 2.4
      It would be nice to find a way to run spark-ec2 with the hadoop-client 2.4.x.
      @see https://groups.google.com/d/msg/spark-users/pHaF01sPwBo/faHr-fEAFbYJ
