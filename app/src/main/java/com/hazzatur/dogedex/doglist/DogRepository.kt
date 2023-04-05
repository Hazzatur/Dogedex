package com.hazzatur.dogedex.doglist

import com.hazzatur.dogedex.R
import com.hazzatur.dogedex.api.ApiResponseStatus
import com.hazzatur.dogedex.api.DogsApi.retrofitService
import com.hazzatur.dogedex.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus {
        return withContext(Dispatchers.IO) {
            try {
                val dogListApiResponse = retrofitService.getAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                val dogDTOMapper = DogDTOMapper()
                ApiResponseStatus.Success(
                    dogDTOMapper.fromDTOListToDogDomainList(dogDTOList)
                )
            } catch (e: UnknownHostException) {
                ApiResponseStatus.Error(R.string.error_unknown_host_exception)
            } catch (e: Exception) {
                ApiResponseStatus.Error(R.string.error_unknown_exception)
            }
        }
    }
}