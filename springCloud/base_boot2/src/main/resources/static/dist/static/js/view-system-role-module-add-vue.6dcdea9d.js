(window.webpackJsonp=window.webpackJsonp||[]).push([["view-system-role-module-add-vue"],{TZGu:function(t,e,r){},lSuC:function(t,e,r){"use strict";var i=r("TZGu");r.n(i).a},s5Ne:function(t,e,r){"use strict";r.r(e);var i=r("zF5t"),s=r("cCY5"),o=r.n(s),n=(r("VCwm"),{components:{Treeselect:o.a},props:{permissions:{type:Array,required:!0}},data:function(){return{dialog:!1,title:"新增角色",form:{name:"",permissions:[],remark:""},permissionIds:[],rules:{name:[{required:!0,message:"请输入名称",trigger:"blur"}]}}},methods:{cancel:function(){this.resetForm()},doSubmit:function(){var t=this;this.$refs.form.validate(function(e){if(!e)return!1;t.form.permissions=[];var r=t;t.permissionIds.forEach(function(t,e){var i={id:t};r.form.permissions.push(i)}),Object(i.a)(t.form).then(function(e){t.resetForm(),t.$notify({title:"添加成功",type:"success",duration:2500}),t.$parent.$parent.init()})})},resetForm:function(){this.dialog=!1,this.$refs.form.resetFields(),this.permissionIds=[],this.form={name:"",permissions:[],remark:""}}}}),a=(r("lSuC"),r("KHd+")),l=Object(a.a)(n,function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",[r("el-button",{staticClass:"filter-item",attrs:{size:"mini",type:"primary",icon:"el-icon-plus"},on:{click:function(e){t.dialog=!0}}},[t._v("新增")]),t._v(" "),r("el-dialog",{attrs:{visible:t.dialog,title:t.title,width:"500px"},on:{"update:visible":function(e){t.dialog=e}}},[r("el-form",{ref:"form",attrs:{model:t.form,rules:t.rules,size:"small","label-width":"66px"}},[r("el-form-item",{attrs:{label:"名称",prop:"name"}},[r("el-input",{staticStyle:{width:"370px"},model:{value:t.form.name,callback:function(e){t.$set(t.form,"name",e)},expression:"form.name"}})],1),t._v(" "),r("el-form-item",{attrs:{label:"权限"}},[r("treeselect",{staticStyle:{width:"370px"},attrs:{multiple:!0,options:t.permissions,placeholder:"请选择权限"},model:{value:t.permissionIds,callback:function(e){t.permissionIds=e},expression:"permissionIds"}})],1),t._v(" "),r("el-form-item",{staticStyle:{"margin-top":"-10px"},attrs:{label:"描述"}},[r("el-input",{staticStyle:{width:"370px"},attrs:{rows:"5",type:"textarea"},model:{value:t.form.remark,callback:function(e){t.$set(t.form,"remark",e)},expression:"form.remark"}})],1)],1),t._v(" "),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"text"},on:{click:t.cancel}},[t._v("取消")]),t._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:t.doSubmit}},[t._v("确认")])],1)],1)],1)},[],!1,null,"11c190f0",null);l.options.__file="add.vue";e.default=l.exports},zF5t:function(t,e,r){"use strict";r.d(e,"d",function(){return s}),r.d(e,"a",function(){return o}),r.d(e,"b",function(){return n}),r.d(e,"c",function(){return a});var i=r("t3Un");function s(){return Object(i.a)({url:"api/roles/tree",method:"get"})}function o(t){return Object(i.a)({url:"api/roles",method:"post",data:t})}function n(t){return Object(i.a)({url:"api/roles/"+t,method:"delete"})}function a(t){return Object(i.a)({url:"api/roles",method:"put",data:t})}}}]);