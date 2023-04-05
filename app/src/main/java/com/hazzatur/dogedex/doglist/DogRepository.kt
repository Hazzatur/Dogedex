package com.hazzatur.dogedex.doglist

import com.hazzatur.dogedex.Dog
import com.hazzatur.dogedex.api.ApiResponseStatus
import com.hazzatur.dogedex.api.DogsApi.retrofitService
import com.hazzatur.dogedex.api.dto.DogDTOMapper
import com.hazzatur.dogedex.api.makeNetworkCall

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDTOListToDogDomainList(dogDTOList)
    }
}