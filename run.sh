export ENV_API_TOKEN={coctoken}
export ENV_DB_USER={postgreuser}
export ENV_DB_PASSWORD={postgrepw}
java -jar -Dspring.profiles.active=dev demo-0.0.1-SNAPSHOT.jar
