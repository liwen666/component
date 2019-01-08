package com.temp.component.dubbo.provider;

import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProduct{
    public String getProductName() {
       
        return "com.temp.component.dubbo.provider";
    }
}