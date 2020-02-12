docker run --rm -p 3306:3306 -e MYSQL_ROOT_PASSWORD=voyager --name fltrail-db -v $(pwd)/mysql:/docker-entrypoint-initdb.d -v /c/dev/amp/htdocs/fltrail/gemeinsamforschen:/test -it mariadb:10.1 bash
