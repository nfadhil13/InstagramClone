package com.fdev.instagramclone.util

abstract class EntityMapper<Entity , Domain>{

    abstract fun mapDomainToEntity(domain: Domain) : Entity

    abstract fun mapEntityToDomain(entity: Entity) : Domain

    abstract fun mapToListEntity(domains : List<Domain>) : List<Entity>

    abstract fun mapToListDomain(entities : List<Entity>) : List<Domain>


}