import domain.Entry
import org.scalacheck.Gen
import org.scalacheck.Prop.forAll

//val absolutePath = "C:\\Users\\rmvieira\\Documents\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf"
//val absolutePath = "C:\\Users\\pefer\\Desktop\\TAP\\1140953_1140956_1141233_a_2019_tap_ncf"
val resourcesPath = "resources\\"
val aircraftResources = resourcesPath + "aircraft\\"

utils.Utils.getAircraftDelay(domain.Class1, domain.Class6)

val agenda = io.XmlAirportIO.loadAgenda(aircraftResources + "agenda.xml")

