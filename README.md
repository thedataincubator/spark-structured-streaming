# Spark Structured Streaming

A short course on the new, experimental features by [The Data Incubator](https://www.thedataincubator.com/) and [O'Reilly Strata](http://conferences.oreilly.com/strata).

## Installation

To run this tutorial, you need Apache Spark and Jupyter.  You can install them:

1. Download and install Apache Spark 2.0.0 by following the [instrucitons here](http://spark.apache.org/docs/latest/).
2. Install Jupyter
```bash
pip install jupyter
```

### Optional
To be able to run the interactive code cells, create a toree kernel:
```bash
jupyter toree install --spark_opts='--master=local[2] --executor-memory 4g --driver-memory 4g' \
    --kernel_name=apache_toree --interpreters=PySpark,SparkR,Scala,SQL --spark_home=$SPARK_HOME
```

Otherwise, you can copy and paste the cells into a spark shell, which you can start by running
```bash
spark-shell
```

**Credits:** The spark project template is based on https://github.com/nfo/spark-project-template
