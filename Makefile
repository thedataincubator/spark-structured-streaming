incremental-compile:
	sbt ~compile

assembly:
	sbt assembly

run: assembly
	spark-submit --master local[2] --class com.thedataincubator.MeetupStreaming.Main target/scala-2.11/MeetupStreaming-assembly-1.0.jar 

notebook:
	cd notebooks && jupyter notebook
