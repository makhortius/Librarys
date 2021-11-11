package vboyko.gb.libs.lesson1.database

import androidx.room.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "age") val age: Int = 0,
)

@Entity(tableName = "t21")
data class Gift(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val uid: Int,
    @ColumnInfo(name = "title") val titleOfGift: String?,
)


@Dao
interface UserDao {

    @Query("SELECT * from user")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}

@Database(
    entities = [
        User::class,
        Gift::class
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}