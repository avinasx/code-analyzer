package com.example.app.repository.custom;

import com.example.app.model.entity.QAddressEntity;
import com.example.app.model.entity.QCityEntity;
import com.example.app.model.entity.QCustomerEntity;
import com.example.app.model.internal.CustomerDetailModel;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CustomerDetailModel> findAllCustomersDetail() {
        var query = findCustomerDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<CustomerDetailModel> findCustomerDetailById(Integer id) {
        var query = findCustomerDetail(id);
        return Optional.ofNullable(query.fetchFirst());
    }

    private JPAQuery<CustomerDetailModel> findCustomerDetail(Integer id) {
        var cu = new QCustomerEntity("cu");
        var a = new QAddressEntity("a");
        var city = new QCityEntity("city");
        var query = jpaQueryFactory
                .select(Projections.constructor(CustomerDetailModel.class,
                        cu.customerId.as("id"),
                        Expressions.asString(cu.fullName.firstName).concat(" ")
                                .concat(cu.fullName.lastName).as("name"),
                        a.address.as("address"),
                        a.postalCode.as("zipCode"),
                        a.phone.as("phone"),
                        city.city.as("city"),
                        city.countryId.as("country"),
                        Expressions.stringTemplate("IF({0}, 'active', '')", cu.active).as("notes"),
                        cu.storeId.as("sid")))
                .from(cu)
                .join(a).on(cu.addressId.eq(a.addressId))
                .join(city).on(a.cityId.eq(city.cityId));
        if (id != null) {
            query.where(cu.customerId.eq(id));
        }
        return query;
    }
}
