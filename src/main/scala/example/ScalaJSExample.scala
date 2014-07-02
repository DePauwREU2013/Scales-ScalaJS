package example

import scala.scalajs.js
import org.scalajs.dom

import edu.depauw.scales.graphics._

object window extends js.Object {
  val innerHeight: Int = ???
}

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    val playground = dom.document.getElementById("playground")

    val paragraph = dom.document.createElement("p")
    paragraph.innerHTML = "<strong>It works! Window height = " + window.innerHeight + ". Using Dynamic, that's " + js.Dynamic.global.window.innerHeight + ".</strong>"
    playground.appendChild(paragraph)

    val canvas = dom.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
    canvas.height = 200
    canvas.width = 600
    playground.appendChild(canvas)

    val p2 = dom.document.createElement("p")
    p2.innerHTML = s"${canvas.width} by ${canvas.height}"
    playground.appendChild(p2)

    import Base._

    val g0 = Rectangle(80, 50).stroke(Color.Red) beside Ellipse(50, 80).rotate(20 deg).translate(0, 50)
    val g1 = g0.fill(RGBA(0, 255, 0, 0.5)).strokeWidth(5)

    val bowtie = Polygon((0, 0), (50, 0), (0, 80), (50, 80))
    val g2 = RegPoly(30, 7).fill(Color.Blue) on bowtie.c.m.fill(HSL(300 deg, 1.0, 0.5))

    val g3 = (Rectangle(50, 50).t beside Rectangle(30, 70).t).fill(Color.Orange)

    val p = List(
      PointSegment(0, 0), LineSegment(1, 1),
      PointSegment(0, 0), LineSegment(0, 1), LineSegment(1, 1), LineSegment(1, 0), CloseSegment)
    val g4 = Shape(p, RectBounds(0, 100, 0, 100)).fill(Color.Clear).stroke(Color.Blue).strokeWidth(5)

    val g5 = Polyline((0, 0), (80, 0), (0, 50), (80, 50))

    val g6 = Shape(180, 180) {
      (0.5, 0.5) lineTo (0, 0) lineTo (1, 0) lineTo (1, 0.5) curveTo (0.5, 1) lineTo (0, 1)
    }.strokeWidth(3).fill(Color.Aqua).stroke(Color.Black)

    val g7 = RegPoly(30, 5, 2).fill(Color.Brown).stroke(Color.Clear)

    val g8 = Bitmap(50, 50) { (x, y) =>
      HSL(x rev, y, 0.5)
    }

    val g9 = g1.pad(0.05).freeze.scale(0.5)

    val g10 = Image("bth.jpg", 70, 80)

    val g = (
      (g4.b.l on g1.showBounds).pad(0.1).t beside
        ((g3 beside HSpace(10) beside g5) above
          VSpace(10) above
          (g2 beside HSpace(10) beside g10.m)).pad(0.1).t beside
        (g7 above g8.c).pad(0.1).t beside
        (g9.r.b on g6.r.m).pad(0.1).t).c above
      (Text("Hello ", Font("serif", 48)).fill(Color.HotPink) beside
        Text("World!", Font("serif", 48), true).stroke(Color.SeaGreen)
    ).pad(0.2).c

    dom.setTimeout(() => g.displayOn(canvas), 1000) // wait for the image to have loaded...
  }

  /**
   * Computes the square of an integer.
   *  This demonstrates unit testing.
   */
  def square(x: Int): Int = x * x
}
