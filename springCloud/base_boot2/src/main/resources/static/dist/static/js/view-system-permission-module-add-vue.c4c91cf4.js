(window.webpackJsonp=window.webpackJsonp||[]).push([["view-system-permission-module-add-vue"],{"5xov":function(t,e,i){"use strict";i.r(e);var r=i("D+s9"),o=i("cCY5"),n=i.n(o),s=(i("VCwm"),{components:{Treeselect:n.a},props:{permissions:{type:Array,required:!0}},data:function(){return{dialog:!1,title:"新增权限",form:{name:"",alias:"",pid:0},rules:{name:[{required:!0,message:"请输入名称",trigger:"blur"}],alias:[{required:!0,message:"请输入别名",trigger:"blur"}]}}},methods:{cancel:function(){this.resetForm()},doSubmit:function(){var t=this;this.$refs.form.validate(function(e){if(!e)return!1;Object(r.a)(t.form).then(function(e){t.resetForm(),t.$notify({title:"添加成功",type:"success",duration:2500}),t.$parent.$parent.init(),t.$parent.$parent.getPermissions()})})},resetForm:function(){this.dialog=!1,this.$refs.form.resetFields(),this.form={name:"",alias:"",pid:0}}}}),a=(i("jQM0"),i("KHd+")),l=Object(a.a)(s,function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("el-button",{staticClass:"filter-item",attrs:{size:"mini",type:"primary",icon:"el-icon-plus"},on:{click:function(e){t.dialog=!0}}},[t._v("新增")]),t._v(" "),i("el-dialog",{attrs:{visible:t.dialog,title:t.title,width:"500px"},on:{"update:visible":function(e){t.dialog=e}}},[i("el-form",{ref:"form",attrs:{model:t.form,rules:t.rules,size:"small","label-width":"80px"}},[i("el-form-item",{attrs:{label:"名称",prop:"name"}},[i("el-input",{staticStyle:{width:"360px"},model:{value:t.form.name,callback:function(e){t.$set(t.form,"name",e)},expression:"form.name"}})],1),t._v(" "),i("el-form-item",{attrs:{label:"别名",prop:"alias"}},[i("el-input",{staticStyle:{width:"360px"},model:{value:t.form.alias,callback:function(e){t.$set(t.form,"alias",e)},expression:"form.alias"}})],1),t._v(" "),i("el-form-item",{staticStyle:{"margin-bottom":"0px"},attrs:{label:"上级类目"}},[i("treeselect",{staticStyle:{width:"360px"},attrs:{options:t.permissions,placeholder:"选择上级类目"},model:{value:t.form.pid,callback:function(e){t.$set(t.form,"pid",e)},expression:"form.pid"}})],1)],1),t._v(" "),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{attrs:{type:"text"},on:{click:t.cancel}},[t._v("取消")]),t._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:t.doSubmit}},[t._v("确认")])],1)],1)],1)},[],!1,null,"148ace24",null);l.options.__file="add.vue";e.default=l.exports},"D+s9":function(t,e,i){"use strict";i.d(e,"d",function(){return o}),i.d(e,"a",function(){return n}),i.d(e,"b",function(){return s}),i.d(e,"c",function(){return a});var r=i("t3Un");function o(){return Object(r.a)({url:"api/permissions/tree",method:"get"})}function n(t){return Object(r.a)({url:"api/permissions",method:"post",data:t})}function s(t){return Object(r.a)({url:"api/permissions/"+t,method:"delete"})}function a(t){return Object(r.a)({url:"api/permissions",method:"put",data:t})}},dQIQ:function(t,e,i){},jQM0:function(t,e,i){"use strict";var r=i("dQIQ");i.n(r).a}}]);