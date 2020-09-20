package com.fdev.instagramclone.framework.datasource.cache.mappers

import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.cache.model.UserCacheEntity
import com.fdev.instagramclone.framework.datasource.network.model.UserNetworkEntity
import com.fdev.instagramclone.util.EntityMapper
import javax.inject.Inject

class UserCacheMapper
@Inject
constructor(

) : EntityMapper<UserCacheEntity, User>() {

    override fun mapDomainToEntity(domain: User): UserCacheEntity {
        return UserCacheEntity(
                id = domain.id!!,
                username = domain.username,
                password = "",
                email = domain.email,
                profileImage = domain.profileImage,
                bio = domain.bio,
                name = domain.name,
                following = domain.following.size,
                followers = domain.followers.size,
                latestLoginTime = 0L
        )
    }

    override fun mapEntityToDomain(entity: UserCacheEntity): User {
        return User(
                id = entity.id,
                username = entity.username,
                password = entity.password,
                email = entity.email,
                profileImage = entity.profileImage,
                bio = entity.bio,
                name = entity.name,
                following = produceFakeUser(entity.following),
                followers = produceFakeUser(entity.followers),
                isRegistered = true
        )
    }

    override fun mapToListEntity(domains: List<User>): List<UserCacheEntity> {
        val entityList = ArrayList<UserCacheEntity>()

        for(domain in domains){
            entityList.add(mapDomainToEntity(domain))
        }
        return entityList
    }

    override fun mapToListDomain(entities: List<UserCacheEntity>): List<User> {
        val domainList = ArrayList<User>()

        for(entity in entities){
            domainList.add(mapEntityToDomain(entity))
        }
        return domainList
    }

    fun produceFakeUser(size : Int) : List<String>{
        val fakeList = ArrayList<String>()

        for(i in 1..size ){
            fakeList.add("")
        }

        return fakeList
    }


}