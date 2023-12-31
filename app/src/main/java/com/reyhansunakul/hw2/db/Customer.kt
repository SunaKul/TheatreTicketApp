package com.reyhansunakul.hw2.db

import android.provider.SyncStateContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reyhansunakul.hw2.util.Constants

@Entity(tableName = Constants.TABLENAME)
class Customer(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name: String,
    @ColumnInfo(name = "lastname")
    var surname: String,
    var debt: Double)
{
    override fun toString(): String {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dept=" + debt +
                '}'
    }
}