package com.biz;

import com.bean.*;

import java.util.*;
import com.util.*;
public class ProductBizImp implements ProductBiz {
    private HashMap<Integer,Product> map=null;

    @Override
    public boolean save(Product pd) {
        //从文件中获取已经存在的商品集合信息
        map=ProductUtil.readProductMapFromFile();
        if(map==null){
            map=new HashMap<>();//创建map集合对象
        }
        /*******实现主键自增的方法*******/
        if(map.size()==0){
            pd.setPid(1);//首次添加商品，商品编号为1
        }else{
            //获取map集合的主键集和
            Set<Integer> ids=map.keySet();
            //获取set集合中的最大编号值
            Integer maxid=Collections.max(ids);
            Integer pid=maxid+1;
            pd.setPid(pid);
        }
        /******************************/
        System.out.println("pd--->"+pd);
        try {
            map.put(pd.getPid(),pd);

            //向文件中保存商品集合对象
            ProductUtil.writeProductMapToFile(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Product pd) {
        //从文件中获取已经存在的商品集合信息
        map=ProductUtil.readProductMapFromFile();
        if(map==null){
            System.out.println("无商品信息存在，请先添加！");
            return false;
        }
        if(map.containsKey(pd.getPid())){//判断商品编号是否存在在集合中
            map.put(pd.getPid(),pd);//更新原来的商品对象
            //向文件中保存已更改的商品集合对象
            ProductUtil.writeProductMapToFile(map);
            return true;
        }

        return false;
    }

    @Override
    public boolean delById(Integer pid) {
        //从文件中获取已经存在的商品集合信息
        map=ProductUtil.readProductMapFromFile();
        if(map==null){
            System.out.println("无商品信息存在，请先添加！");
            return false;
        }
        if(map.containsKey(pid)){//判断商品编号是否存在在集合中
            map.remove(pid);
            //向文件中保存已删除过的商品集合对象
            ProductUtil.writeProductMapToFile(map);
            return true;
        }

        return false;
    }

    @Override
    public Product findById(Integer pid) {
        //从文件中获取已经存在的商品集合信息
        map=ProductUtil.readProductMapFromFile();
        if(map==null){
            System.out.println("无商品信息存在，请先添加！");
            return null;
        }
        if(map.containsKey(pid)) {//判断商品编号是否存在在集合中
            Product pd = map.get(pid);
            return pd;
        }
        return null;
    }

    @Override
    public HashMap<Integer, Product> findAll() {
        //从文件中获取已经存在的商品集合信息
        map=ProductUtil.readProductMapFromFile();
        if(map!=null){
            return map;
        }
        return null;
    }
}
