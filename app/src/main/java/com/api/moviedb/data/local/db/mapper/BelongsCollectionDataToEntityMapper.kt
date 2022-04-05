package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.BelongsCollectionData
import com.api.moviedb.data.remote.model.movieDetails.BelongsToCollection
import javax.inject.Inject

class BelongsCollectionDataToEntityMapper @Inject constructor() :
    Mapper<BelongsToCollection?, BelongsCollectionData?> {
    override fun map(t: BelongsToCollection?): BelongsCollectionData? {
        if(t == null)
            return null

        return BelongsCollectionData(
            id = t.id ?: 0,
            name = t.name ?: "",
            backdropPath = t.backdropPath ?: "",
            posterPath = t.posterPath ?: ""
        )
    }

}
