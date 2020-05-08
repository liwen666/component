package com.temp.common.common.password;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.*;

public class AllSort {
    public static void main(String[] args) throws IOException {
//        char buf[] = {'a', 'b', 'c', 'd', 'e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w'
//        ,'x','y','z','1','2','3','4','5','6','7','8','9','0'};
        char buf[] = {'1','2','3','4','5','6','7','8','9'};
//        static public String[] testString = {"lhcpay!@#666", "SUCCESS", "001","629717920230789024","30.00","2019-09-19 14:15:36","2788926377","小子懿"};
//        abdcae9346d1d0e12b9eda3e67ecc62e
        List<char[]> array = new ArrayList<char[]>();
        conversionArray(0, buf, array);
        /**
         * 切割数组
         */
//        System.out.println(JSON.toJSON(array));



        Map<Integer,List> allNumPass = new HashMap<Integer, List>();
        for(int i = 1;i<buf.length+1;i++){
            allNumPass.put(i,new ArrayList());
        }
        for(char[] cr :array){
            allNumPass.get(cr.length).add(cr);
        }
        /**
         * 给数组分组
         */
        long count1 = 0;
        System.out.println(JSON.toJSON(allNumPass));
        System.out.println("排序数组分类数量是-------------->"+allNumPass.size());
        /*
        排列出所有的组合
         */
        Map<Integer,List<String>> password = new HashMap<Integer, List<String>>(20);
        int num1 = 0;
        long conut = 0;
        for(Map.Entry me:allNumPass.entrySet()){
            File f = new File("D:\\idea2018workspace\\component_new\\common\\src\\main\\java\\com\\temp\\common\\common\\password\\"+me.getKey()+".json");
            if(!f.exists()){
                f.createNewFile();
            }
            num1+=((List<char[]>)me.getValue()).size();
            List<String> numPass = new ArrayList<String>();
            for(char[] b:(List<char[]>)me.getValue()){
                /**
                 * 最对16位以下的密码进行排列
                 */
                if(b.length<=16){
                    perm(b,0,b.length-1,numPass);
                }

            }

            password.put((Integer) me.getKey(),numPass);
            String string = JSON.toJSONString(numPass);
            FileOutputStream fos = new FileOutputStream(f,false);
            fos.write(string.getBytes());
            fos.close();
            System.out.println(me.getKey()+"-->位数的排列种类有---->"+numPass.size());
            conut+=numPass.size();
        }
        /**
         * 获取最终结果
         */
//        System.out.println(string+"最终结果");
        System.out.println("排序数组数量是-------------->"+allNumPass.size());
        System.out.println("全排列数量---------->"+conut);


    }


    private static void conversionArray(int start, char[] buf, List<char[]> array) {
        if (!(start < buf.length)) {
            return;
        }
        int n = buf.length - start;
        for (int j = 0; j < n; j++) {
            char[] arr = new char[j + 1];
            ;
            for (int x = 0; x < j + 1; x++) {
                arr[x] = buf[x + start];
            }
            array.add(arr);
            if (j > 0 && j < 2) {
                for (int y = 0; y < buf.length - j - start; y++) {
                    char[] arrnew = Arrays.copyOf(arr, arr.length);
                    if (y + j + 1 < buf.length - start) {
                        arrnew[j] = buf[y + j + 1 + start];
                        array.add(arrnew);
                    }
                }
            }
            if (j >= 2) {
                for (int y = 0; y < buf.length - j - start; y++) {
                    /**
                     *
                     *
                     *
                     * 4====>6  = 3+2+1
                     * 3===> 3  =2+1
                     * 2====>1  =1
                     */
                    char[] arrnew = Arrays.copyOf(arr, arr.length);
                    if (y + j + 1 < buf.length - start) {
                        arrnew[j] = buf[y + j + 1 + start];
                        array.add(arrnew);
                    }
                }
                int incr = (buf.length - j - start) * (buf.length - j - start - 1) / 2;
//                System.out.println(buf.length - j - start + "==============" + incr);
                /**
                 * char buf[] = {'a', 'b', 'c', 'd', 'f', 'g'};
                 *
                 * ["a","b","c","d"],["a","b","c","f"],["a","b","c","g"]
                 *  /**    2,1
                 //                     * incr=0
                 //                     * incrArr[1]=buf[6-4]
                 //                     * incrArr[2]=buf[6-4+1]
                 //                     * incr=1
                 //                     * incrArr[1]=buf[6-4]
                 //                     * incrArr[2]=buf[6-4+2]
                 //                     * incr=2
                 //                     * incrArr[1]=buf[6-4]
                 //                     * incrArr[2]=buf[6-4+2]
                 //                     * incr=3
                 //                     * incrArr[1]=buf[6-4]
                 //                     * incrArr[2]=buf[6-4+4]
                 //                     * incr=4
                 //                     * incrArr[1]=buf[6-3]
                 //                     * incrArr[2]=buf[6-3+1]
                 //                     */
                int offset=0;
                for (int crr = buf.length - j - start - 1; crr > 0; crr--) {
                    int flag=0;
                    for (int n1 = 0; n1 < crr; n1++) {
                        char[] incrArr = Arrays.copyOf(arr, arr.length);
                        for (int i1 = 1; i1 < arr.length; i1++) {
                            if(i1==arr.length-1){
                                incrArr[i1] = buf[2+i1-1+flag+offset];
                                flag++;
                               continue;
                            }
                            incrArr[i1] = buf[2+i1-1+offset];
                        }
                        array.add(incrArr);
                    }
                    offset++;
                }
//                for(int i=0;i<incr;i++){
//                    /**    4,3,2,1
//                     * incr=0
//                     * incrArr[1]=buf[6-4]
//                     * incrArr[2]=buf[6-4+1]
//                     * incr=1
//                     * incrArr[1]=buf[6-4]
//                     * incrArr[2]=buf[6-4+2]
//                     * incr=2
//                     * incrArr[1]=buf[6-4]
//                     * incrArr[2]=buf[6-4+2]
//                     * incr=3
//                     * incrArr[1]=buf[6-4]
//                     * incrArr[2]=buf[6-4+4]
//                     * incr=4
//                     * incrArr[1]=buf[6-3]
//                     * incrArr[2]=buf[6-3+1]
//                     */
//                    char[] incrArr = Arrays.copyOf(arr, arr.length);
//                    for(int i1 = 1;i1<arr.length;i1++){
//                        incrArr[i1]=buf[incr+arr.length-1+i1-1];
//
//                    }
//                    array.add(incrArr);
//                }
            }

        }
        conversionArray(start+1,buf,array);
    }


    public static void perm(char[] buf, int start, int end,List<String> numPass) throws IOException {
        if (start == end) {//当只要求对数组中一个字母进行全排列时，只要就按该数组输出即可
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i <= end; i++) {
                sb.append(buf[i]);
            }
            numPass.add(sb.toString());

        } else {//多个字母全排列
            for (int i = start; i <= end; i++) {
                char temp = buf[start];//交换数组第一个元素与后续的元素
                buf[start] = buf[i];
                buf[i] = temp;

                perm(buf, start + 1, end,numPass);//后续元素递归全排列

                temp = buf[start];//将交换后的数组还原
                buf[start] = buf[i];
                buf[i] = temp;
            }
        }
    }
}