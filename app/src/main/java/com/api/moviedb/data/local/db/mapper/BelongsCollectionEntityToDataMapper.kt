package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.BelongsCollectionData
import com.api.moviedb.data.remote.model.movieDetails.BelongsToCollection
import javax.inject.Inject

class BelongsCollectionEntityToDataMapper @Inject constructor() :
    Mapper<BelongsCollectionData?, BelongsToCollection?> {
    override fun map(t: BelongsCollectionData?): BelongsToCollection? {
        if (t == null)
            return null
        return BelongsToCollection(
            backdropPath = t.backdropPath,
            id = t.id,
            name = t.name,
            posterPath = t.posterPath
        )
    }

}