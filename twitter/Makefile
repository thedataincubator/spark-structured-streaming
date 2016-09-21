incremental-compile:
	sbt ~compile

assembly:
	sbt assembly

run-print: assembly
	spark-submit --master local[2] \
		--class com.thedataincubator.TwitterStreaming.Main \
		target/scala-2.11/TwitterStructuredStream-assembly-0.1.0.jar

run-save: assembly
	spark-submit --master local[2] \
		--class com.thedataincubator.TwitterStreaming.Main \
		target/scala-2.11/TwitterStructuredStream-assembly-0.1.0.jar output

notebook:
	cd notebooks && jupyter notebook --port 9000

ensime:
	# for Sublime Ensime
	# checkout http://ensime.github.io/build_tools/sbt/
	sbt ensimeConfig

spark-shell:
	cd notebooks && spark-shell --conf "spark.ui.port=5050"

clean:
	rm -Rf output/*
