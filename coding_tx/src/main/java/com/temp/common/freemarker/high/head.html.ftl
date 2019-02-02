<head>
    <title>Title</title>
</head>

<#macro test>
Test text
</#macro>
<#-- call the macro: -->

<#macro abc>
Test text
</#macro>

<#macro paramMac foo bar baaz>
Test text, and the params: ${foo}, ${bar}, ${baaz}
</#macro>