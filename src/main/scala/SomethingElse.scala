import kyo.*
import kyo.App.Effects
import kyo.aborts.*
import kyo.consoles.*
import kyo.direct.*
import kyo.tries.*


object SomethingElse extends App:
  val i1: Int > Any =
    1

  val i2: Int > Consoles =
    defer:
      await(Consoles.println("do it"))
      1

  val t1: String > Tries = Tries.fail(Exception("asdf"))
  val a1: String > Aborts[Int] = Aborts[Int].fail(1)

  val aa1: Either[Int, String] > Any = Aborts[Int].run(a1)

  val e: Unit > (Aborts[String] & Aborts[Int]) =
    defer:
      await(Aborts[String].fail("asdf"))
      await(Aborts[Int].fail(1))
      ()

  // todo: how would we?
  val ef: Unit > Aborts[String | Int] =
    //Aborts[String | Int].catching()
    ???

  // todo: and how would we catch the abort?
  val eff: Unit > Any =
    ???

  def run(args: List[String]): Unit > Effects =
    defer:
      // unhelpful error:
      // await(Consoles.println(await(t1)))

      val thing: Either[Int, String] =
        await(aa1)
      await(Consoles.println(thing))
      //await(Consoles.println(t))
      ()