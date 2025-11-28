package com.example.newsexample.data.db

import androidx.room.TypeConverter
import com.example.newsexample.data.api.Source

class SourceConverter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source( name=name)
    }

}