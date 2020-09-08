#!/bin/bash
DATE=$(date +%Y%m%d)
current_time=$(date +%Y%m%d-%H%M%S)


if [ x"$1" = x ];then
  echo ----请指定执行的操作参数输入  备份 dump  恢复  import
  exit 0
fi
if [ "$1"  = dump ]; then
echo ----开始备份数据库${current_time}------
mysqldump -h192.168.60.136 -uroot -proot anyest3_financial_cloud_101 > anyest3_financial_cloud_101_${current_time}.sql
elif [ "$1"  = import ]; then
  if [ x"$2" = x ]; then
  echo ----请指需要恢复的脚本文件名称
  exit 0
  fi
echo ----开始恢复数据库 脚本是$2------
mysql  -h192.168.60.136  -uroot -proot anyest3_financial_cloud_101 <  $2
fi
