package com.fdev.instagramclone.framework.datasource.network.mapper

import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.network.model.UserNetworkEntity
import com.fdev.instagramclone.util.EntityMapper
import javax.inject.Inject

class UserNetworkMapper @Inject constructor() : EntityMapper<UserNetworkEntity, User>() {
    override fun mapDomainToEntity(domain: User) =
            UserNetworkEntity(
                    id = domain.id,
                    username = domain.username,
                    email = domain.email,
                    profileImage= domain.profileImage,
                    bio = domain.bio,
                    name = domain.name,
                    following = domain.following,
                    followers =domain.followers,
                    isRegistered = domain.isRegistered
            )

    override fun mapEntityToDomain(entity: UserNetworkEntity) =
            User(
                    id= entity.id,
                    username = entity.username,
                    email = entity.email,
                    profileImage =  entity.profileImage,
                    bio = entity.bio,
                    name = entity.name,
                    following = entity.following,
                    followers = entity.followers,
                    isRegistered = entity.isRegistered
            )

    override fun mapToListEntity(domains: List<User>): List<UserNetworkEntity> {
        val entityList = ArrayList<UserNetworkEntity>()

        for(domain in domains){
            entityList.add(mapDomainToEntity(domain))
        }
        return entityList
    }

    override fun mapToListDomain(entities: List<UserNetworkEntity>): List<User> {
        val domainList = ArrayList<User>()

        for(entity in entities){
            domainList.add(mapEntityToDomain(entity))
        }
        return domainList
    }
}