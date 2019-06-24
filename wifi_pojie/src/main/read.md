//列出所有可用wifi
netsh wlan show networks mode=bssid


//添加配置文件

netsh wlan add profile filename=WLAN-TEMP.xml
//列出配置文件
netsh wlan show profile
//连接wifi
netsh wlan connect name=ziroom


//导出配置文件
netsh wlan export profile key=clear
//列出配置文件
netsh wlan show profile
//删除配置文件
netsh wlan delete profile name=FILE_NAME
//列出接口
netsh wlan show interface
//开启接口
netsh interface set interface "Interface Name" enabled

