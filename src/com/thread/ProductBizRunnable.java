package com.thread;
import java.util.*;
import java.io.*;
import java.net.*;
import com.biz.*;
import com.bean.*;

public class ProductBizRunnable implements Runnable {
    private Socket socket;
    private InputStream in;
    private ObjectInputStream objectInputStream;
    private OutputStream out;
    private ObjectOutputStream objectOutputStream;
    private ProductBiz productBiz=new ProductBizImp();

    public ProductBizRunnable(Socket socket) {
        this.socket = socket;
        try {
            in=socket.getInputStream();
            out=socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            //接受客户端传递的操作码
            int op=in.read();

            switch (op){
                case 1://添加
                    Thread.sleep(500);//防止网络阻塞而设置的间隔时间

                    //获取客户端传递的商品对象
                    objectInputStream = new ObjectInputStream(in);
                    Product pd=(Product) objectInputStream.readObject();
                    boolean flag=productBiz.save(pd);
                    //向客户端发送结果码
                    if(flag){
                        out.write(1);//1--保存成功
                    }else{
                        out.write(0);//0--失败
                    }
                    out.flush();
                    break;
                case 2://修改
                    Thread.sleep(500);//防止网络阻塞而设置的间隔时间
                    //获取客户端传递的商品对象
                    objectInputStream = new ObjectInputStream(in);
                    pd=(Product) objectInputStream.readObject();
                    flag=productBiz.update(pd);
                    //向客户端发送结果码
                    if(flag){
                        out.write(1);//1--保存成功
                    }else{
                        out.write(0);//0--失败
                    }
                    out.flush();
                    break;
                case 3://删除
                    //获取客户端传递的要删除的商品编号
                    int pid=in.read();
                    flag=productBiz.delById(pid);
                    //向客户端发送结果码
                    if(flag){
                        out.write(1);//1--保存成功
                    }else{
                        out.write(0);//0--失败
                    }
                    out.flush();

                    break;
                case 4://查找
                    //获取客户端传递的要查找的商品编号
                    pid=in.read();
                    Product oldpd=productBiz.findById(pid);

                    //将查找到的商品对象发送给客户端
                    objectOutputStream =new ObjectOutputStream(out);
                    objectOutputStream.writeObject(oldpd);
                    objectOutputStream.flush();
                    break;
                case 5://显示
                    //获取商品集合对象
                    HashMap<Integer,Product> map=productBiz.findAll();
                    //向客户端发送商品集合对象
                    objectOutputStream =new ObjectOutputStream(out);
                    objectOutputStream.writeObject(map);
                    objectOutputStream.flush();
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
