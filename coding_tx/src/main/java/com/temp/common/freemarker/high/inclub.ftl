插入inclub

<#macro inclub >
导入页面的宏被调用
</#macro>


<head>
    <title>Title</title>
</head>


<#macro paramMac foo bar baaz>
Test text, and the params: ${foo}, ${bar}, ${baaz}
</#macro>

<#macro test111 foo bar="Bar" baaz=-1>
Test text, and the params: ${foo}, ${bar}, ${baaz}
</#macro>

<#macro list title items>
<p>${title?cap_first}: 使字符串第一个字母大写
<ul>
    <#list items as x>
    <li>${x?cap_first}
    </#list>
</ul>
</#macro>

<#macro img src extra...>
<img src="/context${src?html}"
    <#list extra?keys as attr>
    ${attr}="${extra[attr]?html}"
    </#list>
        >
</#macro>

<#macro do_twice>
1. <#nested>
1. <#nested>
2. <#nested>
</#macro>

<#macro do_thrice>
    <#nested 1>
    <#nested 2>
    <#nested 3>
</#macro>