package com.util;
import com.bean.Product;

import java.io.*;
import java.util.*;
public class ProductUtil {
   /**
    * 向文件中写入HashMap集合对象（对象元素为Product对象,主键为商品编号）
    * */
   public static void writeProductMapToFile(HashMap<Integer, Product> map){
       File file=new File("product.bin");

       try {
           FileOutputStream fout=new FileOutputStream(file);
           ObjectOutputStream objectOutputStream=new ObjectOutputStream(fout);//通过文件输出流对象构建对象输出流
           objectOutputStream.writeObject(map);
           objectOutputStream.flush();
           objectOutputStream.close();
           System.out.println("文件保存成功！");
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

   /***
    * 从文件中获取HashMap集合对象
    * */
   public static HashMap<Integer,Product> readProductMapFromFile(){
       File file=new File("product.bin");
       //判断文件是否存在
       if(!file.exists()){//不存在文件
           System.out.println("存储商品的信息不存在，请先添加！");
           return  null;
       }

       try {
           FileInputStream fin=new FileInputStream(file);
           ObjectInputStream objectInputStream=new ObjectInputStream(fin);//通过文件输入流对象构建对象输入流
           HashMap<Integer,Product> map= (HashMap<Integer, Product>) objectInputStream.readObject();

           return map;
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       return null;
   }

//    public static void main(String[] args) {
//        Product pd=new Product(1,"1",1f,1,1);
//        HashMap<Integer,Product> map=new HashMap<>();
//        map.put(1,pd);
//        writeProductMapToFile(map);
//    }
}
