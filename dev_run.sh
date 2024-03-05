#!/bin/sh

#module_name='user-service'
module_name=${1:-user-service}


java -jar $module_name/target/*.jar