# 更新缓存列表的web接口
http://localhost:10100/merchant/cache/updateList

# 根据商户uid更新商户缓存信息
http://localhost:10100/merchant/cache/updateById/111111

http://localhost:10100/merchant/cache/queryMerchantList
http://localhost:10100/merchant/cache/updateList

#后台测试路径
curl http://localhost:20110/dcpay_schedule_cache/merchant/cache/updateById/6 -X GET 

curl http://localhost:20110/dcpay_schedule_cache/merchant/cache/queryMerchantList
curl http://localhost:20110/dcpay_schedule_cache/merchant/cache/updateList

测试环境
curl http://localhost:48083/dcpay_schedule_cache/merchant/cache/queryMerchantList

curl http://localhost:48083/dcpay_schedule_cache/merchant/cache/updateList





#每日商家维度信息需要做定时清理任务
每日零点定时刷新缓存