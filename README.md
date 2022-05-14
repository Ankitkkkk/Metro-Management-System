[![Deploy](https://github.com/Ankitkkkk/Metro-Management-System/actions/workflows/CiCdHeroku.yaml/badge.svg)](https://github.com/Ankitkkkk/Metro-Management-System/actions/workflows/CiCdHeroku.yaml)

## [API STATUS](https://metro-management-system.herokuapp.com/api/status)
# Metro-Management-System
a typical backend project for DMRC daily operations

## Initial Setup for Windows Local
### `git clone https://github.com/Ankitkkkk/Metro-Management-System.git`
### Open intellij inside the folder Metro-Management-System
create a branch from main format `dev-<username>` ignore angular braces
after making changes to your branch
run these commands <br />
`git checkout -b dev-<username>`<br />
`do some changes here`<br />
`git add .`<br />
`git commit -m "a good message for what you have done"`<br />
`git remote add origin https://github.com/Ankitkkkk/Metro-Management-System.git` <br />
`git push origin dev-<username>` <br />

### Local Database Setup
#### run these commands in terminal 
`docker run -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=password --name mongodb mongo` <br />
`docker run --name mysqldb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mysql -d mysql:latest` <br /><br />


login into mysql Database using following commands <br />
`docker exec -it mysqldb bash` <br />
`mysql -u root -p` <br />
then enter password as `mysql` <br /><br />


after login into mySQL shell run the following commands <br />
`create database mms;`<br /><br />


after this we need to configure mongoDB and run these commands in your shell <br />
login into mongoDB shell by these commands <br />
`docker exec -it mongodb bash` <br />
`mongo -u admin -p` <br />
then enter password as `password` <br />
now create a database<br />
`use mms;`

### local database settings are setup

## CAUTION! NEVER PUSH TO `main` BRANCH

