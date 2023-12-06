import kyo.*
import kyo.aborts.Aborts
import kyo.direct.*
import kyo.ios.*
import kyo.consoles.*
import kyo.clocks.*
import kyo.randoms.*

import java.time.Instant


object Main extends App:

  /*
  val a: Int > Any = 1

  val b: Int > Options = a

  val c: Option[Int] > Any =
    Options.run(b)
   */

  // interesting that a `Unit > Consoles` can be overwritten as just `Unit` eliminating the Effect
  // this compiles but isn't correct
  // val p1: Unit = Consoles.println("Starting the app...")

  val p1: Unit > Consoles = Consoles.println("Starting the app...")

  val s1: String > Consoles = Consoles.readln

  val i1: Int > Consoles =
    defer:
      await(Consoles.println("thing"))
      1

  // Randoms effect becomes IOs ???
  val r: Boolean > IOs = Randoms.nextBoolean

  // Clocks effects becomes IOs
  val c: Instant > IOs = Clocks.now

  // why is Consoles not an IOs ?
  val o: Unit > Consoles = Consoles.println("asdf")

  val i2: Int > (Aborts[String] & IOs) =
    defer:
      if await(Randoms.nextBoolean) then 1 else await(Aborts("asdf"))

  def run(args: List[String]) =

    //val i2: Int > IOs & Consoles = IOs(i1)

    defer:
      // in a defer, effects can't be assigned
      // val something = Consoles.println("adsf")

      await:
        p1

      //val b: Boolean = await(IOs(true)) && await(IOs(false))

      val currentTime = await:
        Clocks.now

      await:
        Consoles.println(s"Current time is: $currentTime")

      val randomNumber = await:
        Randoms.nextInt(100)

      await:
        Consoles.println(s"Generated random number: $randomNumber")