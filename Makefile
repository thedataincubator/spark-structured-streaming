notebook:
	cd notebooks && jupyter notebook --port 9000

spark-shell:
	cd notebooks && spark-shell --conf "spark.ui.port=5050"
