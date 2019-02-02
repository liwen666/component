这个类中有对 模板参数的设置
根据源码的查看  所有参数的设定都在这个方法中
public Map<String, Object> getObjectMap(TableInfo tableInfo) {

AbstractTemplateEngine

自顶一个类MyFreemarkerTemplateEngine继承 AbstractTemplateEngine
重写父类的方法，将返回值进行修改即可加入自定义的模板参数