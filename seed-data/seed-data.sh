#!/bin/sh
mongo dit --eval "db.users.drop()"
mongo dit --eval "db.managers.drop()"
mongo dit --eval "db.owners.drop()"
mongo dit --eval "db.staff.drop()"

mongoimport --db dit --collection users --file users.json
mongoimport --db dit --collection owners --file owners.json
mongoimport --db dit --collection managers --file managers.json
mongoimport --db dit --collection staff --file staff.json