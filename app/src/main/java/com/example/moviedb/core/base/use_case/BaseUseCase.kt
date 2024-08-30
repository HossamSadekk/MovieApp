package com.example.moviedb.core.base.use_case

abstract class BaseUseCase<in Params, out Result> {
    abstract suspend fun execute(params: Params): Result
}
