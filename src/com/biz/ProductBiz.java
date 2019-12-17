package com.biz;
import java.util.*;
import com.bean.*;

public interface ProductBiz {
    public boolean save(Product pd);
    public boolean update(Product pd);
    public boolean delById(Integer pid);
    public Product findById(Integer pid);
    public HashMap<Integer,Product> findAll();

}
