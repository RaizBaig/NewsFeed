package com.example.newsfeed.util

/**
 * A sealed class representing the result of a network or database operation.
 *
 * It can be one of the following:
 * - [Success] holds the successful result with the data of type [T].
 * - [Error] holds an error message in case of failure.
 *
 * This class is used to handle the response of operations in a consistent way, such as fetching users or data.
 */
sealed class ResponseHandler<out T> {
    object Loading : ResponseHandler<Nothing>()
    data class Success<out T>(val data: T) : ResponseHandler<T>()
    data class Error(val message: String) : ResponseHandler<Nothing>()
}

