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

<#--空值判断----------------------------------------------------------------------->
<#if isNall ??>${isNall}</#if>

<#--9、集合长度判断-->
<#if list?size != 0>集合长度不是0</#if>

<#--for循环的精简版:-->
<#list 1..3 as x>
${x}
</#list>



15、hash与list的定义
<#assign c= {'a':'orz','b':'czs'}/>
${c.a}
List片段可以采用： products[10..19] or products[5..] 的格式进行定义,当只局限于数字
<#assign c= [1,2,'b',4,5,6,6,7]>
<#list c[1..3] as v>
${v}
</#list>


如果存在大量特殊字符,可以使用${r"..."}进行过滤

${r"${foo}"}
${r"C:\foo\bar"}

!:指定缺失变量的默认值
??:判断某个变量是否存在,返回boolean值
<#if name?? >${name}</#if>
${name! "默认值"}${name! "--"}
<#--<#if name! "xiao" >${name}</#if>-->


24、noparse指令指定FreeMarker不处理该指定里包含的内容,该指令的语法格式如下:
<#noparse>${name! "默认值"}${name! "--"}</#noparse>

<#assign firstName="<input>"/>
${firstName}
25、${firstName?html} 使用html对字符进行格式化处理,对于<等的过滤

        26、escape , noescape指令,对body内的内容实用统一的表达式
        看如下的代码:
<#escape x as x?html>
        First name:${firstName}
        Last name:${lastName!"<666>"}
        <#--Maiden name:${maidenName}-->
</#escape>
        上面的代码等同于:
        First name:${firstName?html}
        Last name:${lastName!"<888>"?html}
        <#--Maiden name:${maidenName?html}-->


        27、定义全局变量的方式
<#assign name1="111" name2="333" /> // 可以同时定义多个变量,也可以使用循环来给变量赋值
<#assign x>
    <#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
    ${n}
    </#list>
</#assign>
${x+name1}


<p>姓名： ${userName}</p>
<p>List:
<#list list as entity>
    ${entity}</br>
</#list></p>
<p>年龄： ${age?if_exists}</p>
</p>

以下是添加import
<#import "head.html.ftl" as my>

调用宏
<@my.test/><@my.abc/>

include的调用
<#include "inclub.ftl" encoding="utf8" parse=true>

<@inclub/>

有参数和默认值宏的调用
<#-- call the macro: -->
<@paramMac foo="a" bar="b" baaz=5*5-2/>
<@my.paramMac foo='my' bar='kkk' baaz=123/>
<@my.paramMac foo='my' baaz=123 bar="jedisHostSingle"/>
<@test111 foo="a" bar="b" baaz=5*5-2/>
<@test111 foo="a" bar="b"/>
<@test111 foo="a" baaz=5*5-2/>
<@test111 foo="a"/>


<@list items=["mouse", "elephant", "python"] title="Animals"/>


<@img src="/images/test.png" width=100 height=50 alt="Test" hhh="jjj"/>

<@do_twice>something</@do_twice>

<@do_thrice ; x>
${x} Anything+${x}.
</@do_thrice>