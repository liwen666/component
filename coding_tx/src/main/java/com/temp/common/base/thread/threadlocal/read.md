对象的threadlocal  属性值的改变只会在当前线程中生效，离开当前线程对象的

threadlocal属性值回归到初始值

threadlocal的初始化需要自己去实现


#这个设计不注意会导致各种内存泄漏
我们从设计的角度要让ThreadLocal的set、remove有始有终，

通常在外部调用的代码中使用finally来remove数据，仅仅要我们细致思考和抽象是能够达到这个目的的


spring  使用threadlocal  在不改变业务系统代码的情况下 
通过线程将connect  传递给业务系统进行事务的管控

thread是线程的全局变量