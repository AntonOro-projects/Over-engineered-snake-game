docker pull mysql
docker run --rm --network host --name snekdb --mount type=bind,src=$(pwd)/my.cnf,dst=/etc/my.cnf -e MYSQL_ROOT_PASSWORD=snekboys -d mysql:latest

