#!/bin/bash

function start(){
   PROJECT_LOCAL=$(pwd)/../output/$PROJECT_NAME
   echo "cur location:$PROJECT_LOCAL"
   cd $PROJECT_LOCAL

   files=$(ls |grep yml)
   RUN_JAR=$(ls |grep jar)

   LOG_LOCATION=/tmp/cloud_demo/$PROJECT_NAME
   mkdir -p $LOG_LOCATION

   for file in $files
   do
      listen_port=${file:0-8:4}
      nohup java -jar $RUN_JAR --spring.config.location=$file >  $LOG_LOCATION/$PROJECT_NAME-$listen_port.log 2>&1 &
   done
}

function stop(){
  pids=$(ps -ef |grep $PROJECT_NAME |awk '{if($3 == 1) print $2}' )
  for pid in $pids
  do
     kill -9 $pid
     echo "kill -9 $pid" 
  done
}


PROJECT_NAME=$1

case $2 in
 "start")
   echo "starting $PROJECT_NAME"
   start
   echo "done"
 ;;
 "stop")
   echo "stoping $PROJECT_NAME"
   stop
   echo " done"
 ;;
 "")
   PROJECT_LOCAL=$(pwd)/../output
   project_names=$(ls -l $PROJECT_LOCAL| awk '{print $9}')
   echo "input like this:"
   for pn in $project_names
   do
     echo "./tool.sh $pn start/stop"
   done
 ;;
esac
