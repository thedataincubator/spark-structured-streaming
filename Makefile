incremental-compile:
	sbt ~compile

assembly:
	sbt assembly

run: assembly
	spark-submit --master local[2] --class io.basilic.myproject.Main target/scala-2.11/spark-project-template-assembly-1.0.jar
