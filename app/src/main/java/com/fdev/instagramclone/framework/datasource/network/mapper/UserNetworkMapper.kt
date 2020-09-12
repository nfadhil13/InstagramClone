package com.fdev.instagramclone.framework.datasource.network.mapper

import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.network.model.UserNetworkEntity
import com.fdev.instagramclone.util.EntityMapper

class UserNetworkMapper : EntityMapper<UserNetworkEntity, User>() {
    override fun mapDomainToEntity(domain: User) =
            UserNetworkEntity(
                    domain.id,
                    domain.username,
                    domain.email,
                    domain.profileImage,
                    domain.following,
                    domain.followers
            )

    override fun mapEntityToDomain(entity: UserNetworkEntity) =
            User(
                    entity.id,
                    entity.username,
                    entity.email,
                    entity.profileImage,
                    entity.following,
                    entity.followers
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