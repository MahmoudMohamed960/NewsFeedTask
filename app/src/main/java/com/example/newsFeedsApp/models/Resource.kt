package com.example.newsFeedsApp.models

data class Resource<out T>(
    val status: Status,
    val responseOne: T?,
    val responseTwo: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(
                status = Status.SUCCESS,
                responseOne = data,
                responseTwo = data,
                message = null
            )

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(
                status = Status.ERROR,
                responseOne = null,
                responseTwo = null,
                message = message
            )

        fun <T> loading(data: T?): Resource<T> =
            Resource(
                status = Status.LOADING,
                responseOne = null,
                responseTwo = null,
                message = null
            )

    }
}