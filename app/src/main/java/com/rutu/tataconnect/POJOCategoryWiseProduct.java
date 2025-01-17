package com.rutu.tataconnect;

public class POJOCategoryWiseProduct
{
    String id,categoryname,shopname,productimage,productname,productprice,productrating,productoffer;

    public POJOCategoryWiseProduct(String id, String categoryname,
                                   String shopname, String productimage,
                                   String productname, String productprice,
                                   String productrating, String productoffer)
    {
        this.id = id;
        this.categoryname = categoryname;
        this.shopname = shopname;
        this.productimage = productimage;
        this.productname = productname;
        this.productprice = productprice;
        this.productrating = productrating;
        this.productoffer = productoffer;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductrating() {
        return productrating;
    }

    public void setProductrating(String productrating) {
        this.productrating = productrating;
    }

    public String getProductoffer() {
        return productoffer;
    }

    public void setProductoffer(String productoffer) {
        this.productoffer = productoffer;
    }
}
