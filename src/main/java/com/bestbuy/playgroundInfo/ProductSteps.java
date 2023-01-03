package com.bestbuy.playgroundInfo;

import com.bestbuy.constants.EndPoint;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {

    @Step("Creating Products with name : {0}, type: {1}, price: {2}, shipping: {3}, upc: {4}, description: {5}, manufacturer: {6}, model: {7}, url: {8}, image: {9}")
    public ValidatableResponse createProduct(String name,
                                             String type,
                                             int price,
                                             int shipping,
                                             String upc,
                                             String description,
                                             String manufacturer,
                                             String model,
                                             String url,
                                             String image) {
        ProductPojo productsPojo = new ProductPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setShipping(shipping);
        productsPojo.setUpc(upc);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post(EndPoint.CREATE_PRODUCTS)
                .then();
    }

    @Step("Getting the Product information with ID: {0}")
    public HashMap<String,?> getProductInfoByName(int productID) {
        HashMap<String,?> productMap = SerenityRest.given().log().all()
                .when()
                .pathParam("productID",productID)
                .get(EndPoint.GET_SINGLE_PRODUCT_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");

        return productMap;
    }

    @Step("Updating Products with ID {0},name: {1}, type: {2}, price: {3}, shipping: {4}, upc: {5}, description: {6}, manufacturer: {7}, model: {8}, url: {9}, image: {10}")
    public ValidatableResponse updatingProduct(int productID,
                                               String name,
                                               String type,
                                               int price,
                                               int shipping,
                                               String upc,
                                               String description,
                                               String manufacturer,
                                               String model,
                                               String url,
                                               String image){

        ProductPojo productsPojo = new ProductPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setShipping(shipping);
        productsPojo.setUpc(upc);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .pathParam("productID",productID)
                .when()
                .patch(EndPoint.UPDATE_SINGLE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Deleting Products with ID {0}")
    public ValidatableResponse deleteProduct(int productID){
        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .delete(EndPoint.DELETE_SINGLE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Getting Products with ID {0}")
    public ValidatableResponse getProductByID(int productID){
        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .get(EndPoint.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }
    }
