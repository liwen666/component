#使用maven打包
  注意！注意！这里有一个巨坑，Common打包出来的应该是不可执行的jar包，所以不要在Common的pom中定义
  spring-boot-maven-plugin插件，因为这个SpringBoot插件会在Maven的package后进行二次打包，目的为了生成可执行jar包，
  如果C中定义了这个插件，会报错提示没有找到main函数。这时你就可以去打包front项目了，当然打包的时候可能还是不行，
  这里还有一个小坑，如果还是不能进行打包的话，那么就install一下root项目，也就是总目录下的pom文件对应的install操作，
  这样再打包front项目基本上就没有问题了，老铁，都是经验呀，希望对你们有帮助
  
#打包失败问题
  先clean一下再试
#install是将jar发送到maven仓库