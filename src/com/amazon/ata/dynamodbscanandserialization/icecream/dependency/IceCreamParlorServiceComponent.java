package com.amazon.ata.dynamodbscanandserialization.classroom.icecream.dependency;

import com.amazon.ata.dynamodbscanandserialization.classroom.icecream.IceCreamParlorAdminService;
import com.amazon.ata.dynamodbscanandserialization.classroom.icecream.IceCreamParlorService;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Declares the dependency roots that Dagger will provide.
 */
@Singleton
@Component(modules = DaoModule.class)
public interface IceCreamParlorServiceComponent {
    IceCreamParlorAdminService provideIceCreamParlorAdminService();
    IceCreamParlorService provideIceCreamParlorService();
}
