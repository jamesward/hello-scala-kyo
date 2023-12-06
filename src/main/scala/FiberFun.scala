import kyo.*
import kyo.App.Effects
import kyo.aborts.*
import kyo.direct.*
import kyo.options.*
import kyo.ios.*
import kyo.consoles.*
import kyo.clocks.*
import kyo.randoms.*
import kyo.concurrent.fibers.*
import scala.concurrent.duration.*

import java.time.Instant

object FiberFun extends App:

  val randomSleep: Int > (Fibers & Consoles) =
    defer:
      val i = await(Randoms.nextInt(10))
      await(Consoles.println(s"sleeping for $i seconds"))
      await(Fibers.sleep(i.seconds))
      i

  val i1: Int > (Fibers & Consoles) =
    val r1: Int > Fibers =
      Consoles.run(randomSleep)
    val r2: Int > Fibers =
      Consoles.run(randomSleep)
    Fibers.race(r1, r2)

  def run(args: List[String]): Unit > Effects =
    defer:
      val i = await(i1)
      await(Consoles.println(s"waited $i seconds"))
