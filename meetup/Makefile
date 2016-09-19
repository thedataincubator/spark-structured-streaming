incremental-compile:
	sbt ~compile

assembly:
	sbt assembly

run-print: assembly
	spark-submit --master local[2] \
		--class com.thedataincubator.MeetupStreaming.Main \
		target/scala-2.11/MeetupStreaming-assembly-1.0.jar 

run-save: assembly
	spark-submit --master local[2] \
		--class com.thedataincubator.MeetupStreaming.Main \
		target/scala-2.11/MeetupStreaming-assembly-1.0.jar output

ensime:
	# for Sublime Ensime
	# checkout http://ensime.github.io/build_tools/sbt/
	sbt ensimeConfig
