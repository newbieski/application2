today=`date +"%Y%m%d%H%M%S"`
dumpfile=dump_${today}.out
targetvm={targetip}
pg_dump -U "postgres" -h {postgredburl} -p 5432 -f ${dumpfile} -d coc
scp ./${dumpfile} root@${targetvm}:{dumppath}${dumpfile}
