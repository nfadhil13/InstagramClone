package com.fdev.instagramclone.business.interactors.auth

import com.fdev.instagramclone.business.data.cache.CacheResponseHandler
import com.fdev.instagramclone.business.data.cache.abstraction.UserCacheDataSource
import com.fdev.instagramclone.business.data.network.NetworkErrors
import com.fdev.instagramclone.business.data.network.NetworkResponseHandler
import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.util.safeApiCall
import com.fdev.instagramclone.business.data.util.safeCacheCall
import com.fdev.instagramclone.business.data.util.safeFirebaseAuthCall
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.framework.presentation.auth.state.SyncAndGetLastUser
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyncAndGetLastUser
@Inject
constructor(
        private val userNetworkDataSource: UserNetworkDataSource,
        private val userCacheDataSource: UserCacheDataSource
) {

    companion object {
        const val ERROR_RETRIEVE_FROM_DB = "Error Retrieve from DB"
        const val NOUSER_YET = "NO USER ON DB YET"
        const val NO_USER_IN_NETWORK = "No user in network"
        const val ERROR_RETRIEVE_FROM_NETWORK = "Error retrieve from network"
    }

    fun sycnAndGetLastUser(
            isNetworkAvailable: Boolean,
            stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {

        printLogD("SyncAndGetLastUser", "Syncing data")

        var result: DataState<AuthViewState>? = null

        val cacheCall: DataState<AuthViewState>? = getLatestLoginUser(stateEvent)


        if (cacheCall != null) {
            val lastUserFromCache = cacheCall.data?.syncAndGetLastUser?.lastUser
            if (lastUserFromCache != null && isNetworkAvailable) {
                val networkCall = checkAndSyncFromNetwork(lastUserFromCache.id, stateEvent)
                val isNull = networkCall?.stateMessage?.response?.message?.contains(NetworkErrors.NETWORK_DATA_NULL)
                if (networkCall != null && isNull != null) {
                    val lastUserFromNetwork = networkCall.data?.syncAndGetLastUser?.lastUser
                    if(isNull){
                        if (lastUserFromNetwork != null) {
                            safeCacheCall(IO) {
                                userCacheDataSource.updateUser(lastUserFromCache)
                            }
                        } else {
                            safeCacheCall(IO) {
                                userCacheDataSource.deleteUser(lastUserFromCache.id)
                            }
                        }
                    }
                    result = cacheCall
                } else {
                    result = DataState.error<AuthViewState>(
                            response = Response(
                                    message = ERROR_RETRIEVE_FROM_NETWORK,
                                    uiComponentType = UIComponentType.Dialog(),
                                    messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                    )
                }
            } else {
                result = cacheCall
            }
        } else {
            result = DataState.error<AuthViewState>(
                    response = Response(
                            message = ERROR_RETRIEVE_FROM_DB,
                            uiComponentType = UIComponentType.Dialog(),
                            messageType = MessageType.Error()
                    ),
                    stateEvent = stateEvent
            )
        }
        emit(result)

    }


    private suspend fun checkAndSyncFromNetwork(userId: String, stateEvent: StateEvent): DataState<AuthViewState>? {

        val apiCall = safeApiCall(IO, {
            safeFirebaseAuthCall(it)
        }) {
            var user: User? = null
            user = userNetworkDataSource.getUser(userId)
            if (user != null) {
                printLogD("SyncAndGetLastUser", "email : ${user.email} , ${user.password}")
                userNetworkDataSource.loginWithEmail(user.email, user.password)
            }
            user
        }

        return object : NetworkResponseHandler<AuthViewState, User>(
                stateEvent = stateEvent,
                response = apiCall
        ) {
            override suspend fun handleSuccess(resultObj: User): DataState<AuthViewState>? {
                return DataState.data(
                        response = null,
                        data = AuthViewState(
                                syncAndGetLastUser = SyncAndGetLastUser(resultObj, isLogin = true)
                        ),
                        stateEvent = null)

            }

        }.getResult()
    }

    private suspend fun getLatestLoginUser(stateEvent: StateEvent): DataState<AuthViewState>? {
        val cacheCall = safeCacheCall(IO) {
            userCacheDataSource.getLatestLoginUser()
        }

        return object : CacheResponseHandler<AuthViewState, User?>(
                stateEvent = stateEvent,
                response = cacheCall
        ) {
            override fun handleSuccess(resultObj: User?): DataState<AuthViewState>? {
                return DataState.data(
                        response = null,
                        data = AuthViewState(
                                syncAndGetLastUser = SyncAndGetLastUser(resultObj, isLogin = true)
                        ),
                        stateEvent = null)

            }

        }.getResult()
    }

}