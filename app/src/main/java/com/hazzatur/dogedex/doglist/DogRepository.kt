package com.hazzatur.dogedex.doglist

import com.hazzatur.dogedex.Dog
import com.hazzatur.dogedex.api.DogsApi.retrofitService
import com.hazzatur.dogedex.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun downloadDogs(): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = retrofitService.getAllDogs()
            if (response.isSuccess) {
                val dogDTOMapper = DogDTOMapper()
                dogDTOMapper.fromDTOListToDogDomainList(response.data.dogs)
            } else {
                emptyList()
            }
        }
    }
}