package com.api.common

interface Mapper<Param, Result> {
    fun map(t: Param): Result
}