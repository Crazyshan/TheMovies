
package com.skydoves.themovies.models

import com.google.gson.Gson

/**
 * Developed by skydoves on 2018-08-09.
 * Copyright (c) 2018 skydoves rights reserved.
 */

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    var errorEnvelope: ErrorEnvelope? = null

    init {
        message?.let {
            val gson = Gson()
            errorEnvelope = gson.fromJson(message, ErrorEnvelope::class.java) as ErrorEnvelope
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val resource = o as Resource<*>

        if (status !== resource.status) {
            return false
        }
        if (if (message != null) message != resource.message else resource.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS, data =  data, message =  null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(status = Status.ERROR, data = data, message = msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, data = data, message = null)
        }
    }
}