package cpsc411.homework1

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class Database constructor (var dbName : String = "") {

    init {
        dbName = "C:\\Users\\Grant\\Desktop\\homework1.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        val sqlStmt = "create table if not exists claim (id text, title text, date text, isSolved text)"
        dbConn.exec(sqlStmt)
    }

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn

    }

    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj as Database
        }
    }
}