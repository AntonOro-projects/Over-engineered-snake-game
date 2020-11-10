docker pull mysql
docker run --rm --network host --name snekdb -v $(pwd)/data:/var/lib/mysql --mount type=bind,src=$(pwd)/my.cnf,dst=/etc/my.cnf -e MYSQL_ROOT_PASSWORD=snekboys -d mysql:latest



