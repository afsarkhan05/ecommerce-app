package com.zubi.ecommerce.common.enums;

public enum ProductStatus {

    AVAILABLE("available"), OUT_OF_STOCK("out of stock"), NOT_AVAILABLE("not available");
    private String status;
    ProductStatus(final String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
