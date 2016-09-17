incremental-compile:
	sbt ~compile

assembly:
	sbt assembly

run: assembly
	spark-submit --master local[2] --class com.thedataincubator.MeetupStreaming.Main target/scala-2.11/MeetupStreaming-assembly-1.0.jar 

notebook:
	cd notebooks && jupyter notebook --port 9000

ensime:
	# for Sublime Ensime
	# checkout http://ensime.github.io/build_tools/sbt/
	sbt ensimeConfig

spark-shell:
	cd notebooks && spark-shell --conf "spark.ui.port=5050"
