package sm_tv.com.cardatabase.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "CarData")

data class CarData(
    @PrimaryKey(autoGenerate = true)
    val uid:Int,

    @ColumnInfo(name = "Url")
    val url: String,

    @ColumnInfo(name = "Name")
    val name: String,

    @ColumnInfo(name = "YearIssue")
    val yearIssue: Int,

    @ColumnInfo(name = "Classification")
    val classification: String,

    @ColumnInfo(name = "Timestamp")
    val timestamp: Long
): Parcelable
