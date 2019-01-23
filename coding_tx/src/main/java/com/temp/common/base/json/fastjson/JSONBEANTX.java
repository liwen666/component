package com.temp.common.base.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.temp.common.base.json.fastjson.bean.Course;
import com.temp.common.base.json.fastjson.bean.Student;
import com.temp.common.base.json.fastjson.bean.Teacher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JSONBEANTX {
    //json字符串-简单对象型
    private static final String JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";
    //json字符串-数组类型
    private static final String JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";
    //复杂格式json字符串
    private static final String COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";

    /**
     * json字符串-简单对象与JavaBean_obj之间的转换
     */
    @Test
    public  void testJSONStrToJavaBeanObj(){

        Student student = JSON.parseObject(JSON_OBJ_STR, new TypeReference<Student>() {});
        //Student student1 = JSONObject.parseObject(JSON_OBJ_STR, new TypeReference<Student>() {});//因为JSONObject继承了JSON，所以这样也是可以的

        System.out.println(student.getStudentName()+":"+student.getStudentAge());
        System.out.println(JSON.toJSON(student));

    }

    /**
     * json字符串-数组类型与JavaBean_List之间的转换
     */
    @Test
    public  void testJSONStrToJavaBeanList(){

        ArrayList<Student> students = JSON.parseObject(JSON_ARRAY_STR, new TypeReference<ArrayList<Student>>() {});
        //ArrayList<Student> students1 = JSONArray.parseObject(JSON_ARRAY_STR, new TypeReference<ArrayList<Student>>() {});//因为JSONArray继承了JSON，所以这样也是可以的

        for (Student student : students) {
            System.out.println(student.getStudentName()+":"+student.getStudentAge());
        }
        System.out.println(JSON.toJSON(students));
    }

    /**
     * 复杂json格式字符串与JavaBean_obj之间的转换
     */
    @Test
    public  void testComplexJSONStrToJavaBean(){

        Teacher teacher = JSON.parseObject(COMPLEX_JSON_STR, new TypeReference<Teacher>() {});
        //Teacher teacher1 = JSON.parseObject(COMPLEX_JSON_STR, new TypeReference<Teacher>() {});//因为JSONObject继承了JSON，所以这样也是可以的
        String teacherName = teacher.getTeacherName();
        Integer teacherAge = teacher.getTeacherAge();
        Course course = teacher.getCourse();
        List<Student> students = teacher.getStudents();
        for (Student student : students) {
            System.out.println(student.getStudentName()+":"+student.getStudentAge());
        }
        System.out.println(JSON.toJSON(teacher));
    }

}
