# concatenate all files into one big json
for f in tt*.json
do
	cat $f >> imdb.json
	echo "," >> imdb.json
done
