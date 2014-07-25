package example

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._
import edu.depauw.scales.music._

object window extends js.Object {
  val innerHeight: Int = ???
}

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    val playground = dom.document.getElementById("playground")

    val paragraph = dom.document.createElement("p")
    paragraph.innerHTML = "<strong>It works! Window height = " + window.innerHeight + ". Using Dynamic, that's " + js.Dynamic.global.window.innerHeight + ".</strong>"
    playground.appendChild(paragraph)

    ///---------------///
    import Base._
    import Reactive._

    def fnMouse(xy: (Int, Int)): Graphic = {
      Shape(180, 180) {
        (0.5, 0.5) lineTo
        (0, 0) lineTo
        (1, 0) lineTo
        (1, 0.5) curveTo
        (0.5, 1) lineTo
        (0, 1)
      }.strokeWidth(3).fill(Color.HotPink).stroke(Color.Aqua).translate(xy._1, xy._2)
    }

    def fnTime(t: Double): Graphic = {
      (Ellipse(50,75).fill(Color.Blue) on Ellipse(75, 75).fill(Color.SeaGreen)).translate(t * 30, 100)
    }

    def fnTime2(t: Double): Graphic = {
      (Rectangle(80, 50).fill(Color.Lilac).stroke(Color.Red) beside Ellipse(50, 80).fill(Color.Mustard).rotate(20 deg)).translate(100, t * 50)
    }

    Reactor(MouseClick, fnMouse)
    Reactor(ClockTick(1, 20), fnTime)
    Reactor(ClockTick(5, 20), fnTime2)
    //Reactor(MouseClickX, fnSounds)


    val n1 = Note(444)
    val n2 = Note(400)
    val n3 = Note(600)

    val n4 = Note(200, 1, 20)
    val n5 = Note(100)

    (((n1 before n2) before n3) par (n4 before n5)).play()

    def fnNotes(t: Double): ScalesNote = {
      (A after B) par (C after D)
    }








    // val canvas = dom.document.getElementById("output").asInstanceOf[dom.HTMLCanvasElement]
    // canvas.height = 200
    // canvas.width = 600

    // val p2 = dom.document.createElement("p")
    // p2.innerHTML = s"${canvas.width} by ${canvas.height}"
    // playground.appendChild(p2)

    // import Base._

    // val g0 = Rectangle(80, 50).stroke(Color.Red) beside Ellipse(50, 80).rotate(20 deg).translate(0, 50)
    // val g1 = g0.fill(RGBA(0, 255, 0, 0.5)).strokeWidth(5)

    // val bowtie = Polygon((0, 0), (50, 0), (0, 80), (50, 80))
    // val g2 = RegPoly(30, 7).fill(Color.Blue) on bowtie.center.fill(HSL(300 deg, 1.0, 0.5))

    // val g3 = (Rectangle(50, 50).top beside Rectangle(30, 70).top).fill(Color.Orange)

    // val p = List(
    //   PointSegment(0, 0), LineSegment(1, 1),
    //   PointSegment(0, 0), LineSegment(0, 1), LineSegment(1, 1), LineSegment(1, 0), CloseSegment)
    // val g4 = Shape(p, RectBounds(0, 100, 0, 100)).fill(Color.Clear).stroke(Color.Blue).strokeWidth(5)

    // val g5 = Polyline((0, 0), (80, 0), (0, 50), (80, 50))

    // val g6 = Shape(180, 180) {
    //   (0.5, 0.5) lineTo
    //   (0, 0) lineTo
    //   (1, 0) lineTo
    //   (1, 0.5) curveTo
    //   (0.5, 1) lineTo
    //   (0, 1)
    // }.strokeWidth(3).fill(Color.Aqua).stroke(Color.Black)

    // val g7 = RegPoly(30, 5, 2).fill(Color.Brown).stroke(Color.Clear)

    // val g8 = Bitmap(50, 50) { (x, y) =>
    //   HSL(x rev, y, 0.5)
    // }

    // val g9 = g1.pad(0.05).freeze.scale(0.5)

    // val g10 = Image("bth.jpg", 70, 80)

    // val g = (
    //   (g4.bl on g1.showBounds).pad(0.1).top beside
    //     ((g3 beside HSpace(10) beside g5) above
    //       VSpace(10) above
    //       (g2 beside HSpace(10) beside g10.center)).pad(0.1).top beside
    //     (g7 above g8.center).pad(0.1).top beside
    //     (g9.br on g6.right).pad(0.1).top).center above
    //   (Text("Hello ", Font("serif", 48)).fill(Color.HotPink) beside
    //     Text("World!", Font("serif", 48), true).stroke(Color.SeaGreen)
    // ).pad(0.2).center

    // dom.setTimeout(() => g.displayOn(canvas), 1000) // wait for the image to have loaded...
    
  }

  /**
   * Computes the square of an integer.
   *  This demonstrates unit testing.
   */
  def square(x: Int): Int = x * x
}
