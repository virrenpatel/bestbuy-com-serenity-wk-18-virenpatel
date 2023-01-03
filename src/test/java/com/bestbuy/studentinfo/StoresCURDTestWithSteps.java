package com.bestbuy.studentinfo;


import com.bestbuy.playgroundInfo.StoreSteps;
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
public class StoresCURDTestWithSteps extends TestBase {

    static String name = "Manan" + TestUtils.getRandomValue();
    static String type = "CarDealer" + TestUtils.getRandomValue();
    static String address = "16 Luton Road";
    static String address2 = "27 luton";
    static String city = "London";
    static String state = "UK";
    static String zip = "LU4 8HA";
    static int lat = 10;
    static int lng = 15;
    static String hours = "Mon: 9-5; Tue: 9-5; Wed: 9-5;Thurs: 9-5; Fri: 9-5; Sat: 9-5; Sun: 9-2";

    static int storeID;

    @Steps
    StoreSteps storesSteps;

    @Title("This will create a New Store")
    @Test

    public void test001() {
        HashMap<Object, Object> servicesData = new HashMap<>();
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours, servicesData);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
        System.out.println(storeID);
    }

    @Title("Verify if the Store was added to the application")
    @Test

    public void test002() {
        HashMap<String, ?> storeMap = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(storeMap, hasValue(name));
        System.out.println(storeMap);
    }

    @Title("Update the Store information")
    @Test

    public void test003() {
        name = name + "_updated";
        HashMap<Object, Object> servicesData = new HashMap<>();
        storesSteps.updateStore(storeID, name, type, address, address2, city, state, zip, lat, lng, hours, servicesData);
        HashMap<String, ?> productList = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @Title("Delete the Store by ID")
    @Test

    public void test004() {
        storesSteps.deleteStore(storeID).statusCode(200);
        storesSteps.getStoreByID(storeID).statusCode(404);
    }


}
