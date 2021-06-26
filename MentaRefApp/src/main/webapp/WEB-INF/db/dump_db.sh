#!/bin/bash

mysqldump -h localhost -u menta -pmenta --no-create-db --add-locks=FALSE --lock-tables=FALSE --databases Menta | perl -pe 's/^\/\*.+\*\/;$//g' | perl -pe 's/\) ENGINE.+$/\);/g' | perl -pe 's/^USE.+$//g' > menta.sql

