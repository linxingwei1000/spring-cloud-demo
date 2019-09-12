#!/bin/bash


#get cur lacation
PROJECT_LOCAL=$(pwd)/../..
echo "cur location: $PROJECT_LOCAL"
cd $PROJECT_LOCAL
mvn clean package

#clean pre file
TARGET_PATH=$PROJECT_LOCAL/tool/output
echo "clean pre file:$TARGET_PATH"
rm -rf $TARGET_PATH/*

#get catalog filename
files=$(ls -l $PROJECT_LOCAL | grep "^d" | awk '{if($9 != "common" && $9 != "tool") print $9}')

for file in $files
   do
      cur_file_path=$PROJECT_LOCAL/$file
      jar_path=$cur_file_path/target/*.jar
      config_path=$cur_file_path/target/classes/*.yml

      cur_local_tmp=$TARGET_PATH/$file
      mkdir $cur_local_tmp
      mv -f $jar_path $cur_local_tmp
      mv -f $config_path $cur_local_tmp
   done
echo "move done from:$PROJECT_LOCAL to $TARGET_PATH"

tar -zcvf $PROJECT_LOCAL/demo.tar.gz $PROJECT_LOCAL/tool
