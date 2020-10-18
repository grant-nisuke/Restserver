package cpsc411.homework1

import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.server.engine.ConnectorType
import io.ktor.utils.io.readAvailable
import com.almworks.sqlite4java.SQLiteConnection
import com.google.gson.Gson
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    //single query, based on specifics
    routing {
       // this.post("/ClaimService/add") {
       //     var isSolved: Boolean? = false
       //     println("HTTP message is using GET method with /get ")
       //     val id = UUID.randomUUID()
       //     val title: String? = call.request.queryParameters["title"]
       //     val date: String? = call.request.queryParameters["date"]
       //     val temptruth: String? = call.request.queryParameters["isSolved"]
       //     val response = String.format("id %s , title %s , date %s, isSolved %s", id, title, date, isSolved)

       //     isSolved = temptruth?.toBoolean()

       //     val pObj = Claim(id.toString(), title, date, isSolved)
       //     val dbObj = Database.getInstance()
       //     val dao = ClaimSao().addClaim(pObj)
       //     call.respondText(response, status = HttpStatusCode.OK, contentType = ContentType.Text.Plain)
       // }

        this.post(path = "/ClaimService/add") {
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output)      //for further processing

            // JSON serialization/deserialization THIS IS THE ONE I DID
            val dbObj = Database.getInstance()
            val pObj = Gson().fromJson(str, Claim::class.java)
            pObj.id = UUID.randomUUID().toString()
            pObj.isSolved = false
            val dao = ClaimSao().addClaim(pObj)
            // GSON (Google library)

            println("HTTP message is using POST method with /post ${contType} ${str}")
            //call.respondText("The POST request was successfully processed. ", status = HttpStatusCode.OK, contentType = ContentType.Text.Plain)
            call.respondText(str, status = HttpStatusCode.OK, contentType = ContentType.Text.Plain)

        }
        this.get(path = "/ClaimService/getAll") {
            val pList = ClaimSao().getAll()
            println("The number of students : ${pList.size}")
            // JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(pList)
            call.respondText(respJsonStr, status=HttpStatusCode.OK, contentType= ContentType.Text.Plain)
        }
    }
}

