package com.bestbuy.studentinfo;


import com.bestbuy.playgroundInfo.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class
ProductCURDTestWithSteps extends TestBase {

    static String name = "MShah" + TestUtils.getRandomValue();
    static String type = "cars" + TestUtils.getRandomValue();
    static Integer price = 9;
    static Integer shipping = 27;
    static String upc = "020221340";
    static String description = "Black color";
    static String manufacturer = "AUDI";
    static String model = "A3";
    static String url = " ";
    static String image = " ";
    static int productID;

    @Steps
    ProductSteps productsSteps;

    @Title("This will create a New product")
    @Test
    public void test001() {


        ValidatableResponse response = productsSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
        productID = response.log().all().extract().path("id");
        System.out.println(productID);
    }

    @Title("Verify if the Product was added to the application")
    @Test
    public void test002() {
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @Title("Update the product information")
    @Test
    public void test003() {
        name = name + "_updated";
        productsSteps.updatingProduct(productID, name, type, price, shipping, upc, description, manufacturer, model, url, image);
        HashMap<String, ?> productMap = productsSteps.getProductInfoByName(productID);
        Assert.assertThat(productMap, hasValue(name));
        System.out.println(productMap);
    }

    @Title("Delete the product by ID")
    @Test
    public void test004() {
        productsSteps.deleteProduct(productID).statusCode(200);
        productsSteps.getProductByID(productID).statusCode(404);
    }
}
