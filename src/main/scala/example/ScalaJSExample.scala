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
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    playground.appendChild(canvas)

    val p2 = dom.document.createElement("p")
    p2.innerHTML = s"${canvas.width} by ${canvas.height}"
    playground.appendChild(p2)

    import Base._

    val g = Rectangle(80, 50).stroke(Color.Red) beside Ellipse(50, 80).rotate(20 deg).translate(0, 50)
    val g1 = g.fill(RGBA(0, 255, 0, 0.5)).strokeWidth(5)
    g1.translate(200, 105).render(ctx)

    val bowtie = Polygon((0, 0), (50, 0), (0, 80), (50, 80))
    val g2 = RegPoly(30, 7).fill(Color.Blue) on bowtie.c.m.fill(HSL(300 deg, 1.0, 0.5))
    g2.translate(100, 150).render(ctx)

    val g3 = (Rectangle(50, 50).t beside Rectangle(30, 70).t).fill(Color.Orange)
    g3.translate(30, 10).render(ctx)

    val p = List(
      PointSegment(0, 0), LineSegment(1, 1),
      PointSegment(0, 0), LineSegment(0, 1), LineSegment(1, 1), LineSegment(1, 0), CloseSegment)
    val g4 = Shape(p, RectBounds(0, 100, 0, 100)).fill(Color.Clear).stroke(Color.Blue).strokeWidth(5)
    g4.translate(200, 5).render(ctx)

    val g5 = Polyline((0, 0), (80, 0), (0, 50), (80, 50))
    g5.translate(100, 10).render(ctx)
    
    val g6 = Shape(180, 180) {
      (0.5, 0.5) lineTo (0, 0) lineTo (1, 0) lineTo(1, 0.5) curveTo (0.5, 1) lineTo (0, 1)
    }.strokeWidth(3).fill(Color.Aqua).stroke(Color.Black)
    g6.translate(410, 10).render(ctx)
    
    val g7 = RegPoly(30, 5, 2).fill(Color.Brown).stroke(Color.Clear)
    g7.translate(380, 50).render(ctx)
    
    val g8 = Bitmap(50, 50) {(x, y) =>
      HSL(x rev, y, 0.5)
    }
    g8.translate(400, 80).render(ctx)
  }

  /**
   * Computes the square of an integer.
   *  This demonstrates unit testing.
   */
  def square(x: Int): Int = x * x
}
