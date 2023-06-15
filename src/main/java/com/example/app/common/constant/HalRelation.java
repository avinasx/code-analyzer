package com.example.app.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HalRelation {
    // relation
    private String collection;
    private String item;

    // link
    private String self;
    private String _embedded;
    private String _links;
    private String first;
    private String last;
    private String previous;
    private String next;
    private String page;
    private String size;

    // tasks
    private String create;
    private String read;
    private String update;
    private String delete;
    private String remove;
    private String search;
    private String details;

    // core
    private String actorList;
    private String actor;
    private String addressList;
    private String address;
    private String categoryList;
    private String category;
    private String cityList;
    private String city;
    private String countryList;
    private String country;
    private String customerList;
    private String customer;
    private String filmActorList;
    private String filmActor;
    private String filmCategoryList;
    private String filmCategory;
    private String filmList;
    private String film;
    private String filmTextList;
    private String filmText;
    private String inventoryList;
    private String inventory;
    private String languageList;
    private String language;
    private String paymentList;
    private String payment;
    private String rentalList;
    private String rental;
    private String staffList;
    private String staff;
    private String storeList;
    private String store;
    private String fullName;

    // extra
    private String actorDetailsList;
    private String actorDetails;
    private String categorySalesList;
    private String categorySales;
    private String customerDetailsList;
    private String customerDetails;
    private String filmDetailsList;
    private String filmDetails;
    private String staffDetailsList;
    private String staffDetails;
    private String storeDetailsList;
    private String storeDetails;
    private String storeSalesList;
    private String storeSales;

    // actor controller methods
    private String getActorList;
    private String addActor;
    private String getActor;
    private String updateActor;
    private String deleteActor;
    private String getActorDetails;
    private String getActorFilmList;
    private String addActorFilm;
    private String getActorFilm;
    private String deleteActorFilm;
    private String getActorFilmDetails;
    private String searchActorByName;
}
