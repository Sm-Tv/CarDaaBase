package sm_tv.com.cardatabase.model

import androidx.room.ColumnInfo

data class BrandCar(
    @ColumnInfo(name = "Name")
    val name: String,
)
