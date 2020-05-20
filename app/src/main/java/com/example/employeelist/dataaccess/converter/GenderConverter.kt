package com.example.employeelist.dataaccess.converter

import androidx.room.TypeConverter
import com.example.employeelist.dataaccess.database.entity.Gender

class GenderConverter {

    @TypeConverter
    fun fromGender(gender: Gender): Int? = gender.ordinal

    @TypeConverter
    fun toGender(gender: Int): Gender = when (gender) {
        0 -> Gender.MALE
        else -> Gender.FEMALE
    }
}