package cpsc411.homework1

import com.google.gson.Gson
import cpsc411.homework1.Claim
import cpsc411.homework1.Sao

class ClaimSao : Sao() {

    fun addClaim(pObj : Claim) {
        val conn = Database.getInstance()?.getDbConnection()


        sqlStmt = "Insert into claim (id, title, date, isSolved) values ('${pObj.id}', '${pObj.title}', '${pObj.date}', '${pObj.isSolved}')"
        val jsonStyle = Gson().toJson(pObj)
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {
        val conn = Database.getInstance()?.getDbConnection()

        sqlStmt = "Select id, title, date, isSolved from claim"

        var claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        while (st?.step()!!) {
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnString(3)
            val isSolvedConvert = isSolved.toBoolean()
            claimList.add(Claim(id, title, date, isSolvedConvert))
        }
        return claimList
    }
}