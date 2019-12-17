package com.server;
import java.net.*;
import java.io.*;
import com.thread.*;
public class ProductServer {
    private ServerSocket serverSocket;
    private Socket socket;
    private ProductBizRunnable productBizRunnable;
    public ProductServer() {
        try {
            //绑定服务器端口
            serverSocket=new ServerSocket(8899);
            System.out.println("服务器已经启动，请勿关闭......");

            while(true){
                socket=serverSocket.accept();
                productBizRunnable=new ProductBizRunnable(socket);
                Thread th=new Thread(productBizRunnable);
                th.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
