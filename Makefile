notebook:
	cd notebooks && jupyter notebook --port 9000

spark-shell:
	cd notebooks && spark-shell

clean-output:
	python tools/clean_output.py notebooks/*.ipynb

generate-playbook:
	python tools/generate_playbook.py notebooks/*.ipynb
