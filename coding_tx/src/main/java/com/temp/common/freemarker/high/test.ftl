<#--判断变量是否存在---------------------------------------------------------->
<#--${book.name?if_exists } //用于判断如果存在,就输出这个值-->
<#--${book.name?default(‘xxx’)}//默认值xxx-->
<#--${book.name!"xxx"}//默认值xxx-->
<#--${book.date?string('yyyy-MM-dd')} //日期格式-->
<#--${book?string.number} 20 //三种不同的数字格式-->
<#--${book?string.currency}--&lt;#&ndash; $20.00 &ndash;&gt;-->
<#--${book?string.percent}—&lt;#&ndash; 20% &ndash;&gt;-->
<#--定义变量----------------------------------------------------->
<#assign foo=true />
<#--//声明变量,插入布尔值进行显示-->
<#--${foo?string("yes","no")}-->
${foo?string("成功","失败")}

<#--判断大小------------------------------------------------>
<#assign x=10 /><#assign y=20 />
<#--使用lt、lte、gt和gte来替代<、<=、>和>= 也可以使用括号<#if (x>y)>-->
<#if x lt y>x小于y</#if>


<#--常见的一些内置函数=========================================================-->
<#--对于字符串-->
<#--html－对字符串进行HTML编码-->
<#--cap_first－使字符串第一个字母大写-->
<#--lower_case－将字符串转换成小写-->
<#--trim－去掉字符串前后的空白字符-->

${'freeMarker'?cap_first}

<#--空值判断-------------------------------------->
<#if isNall ??>${isNall}</#if>



<p>姓名： ${userName}</p>
<p>List:
<#list list as entity>
    ${entity}</br>
</#list></p>
<p>年龄： ${age?if_exists}</p>
</p>