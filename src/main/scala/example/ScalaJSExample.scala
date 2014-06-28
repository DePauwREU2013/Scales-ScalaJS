package example

import scala.scalajs.js
import org.scalajs.dom

import edu.depauw.scales.graphics._

object window extends js.Object {
  override def toString: String = ???
}

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    val playground = dom.document.getElementById("playground")
    
    val paragraph = dom.document.createElement("p")
    paragraph.innerHTML = "<strong>It works! Window = " + window.toString + "</strong>"
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
    
    val g = Rectangle(80, 50).stroke(Color.red) beside Ellipse(50, 80).rotate(20 deg).translate(0, 50)
    val g1 = g.fill(RGBA(0, 255, 0, 0.5)).width(5)
    g1.translate(200, 105).render(ctx)
    
    val g2 = RegPoly(30, 6).fill(Color.blue) on Ellipse(50, 80).fill(HSL(300 deg, 1.0, 0.5))
    g2.translate(100, 150).render(ctx)
    
    val g3 = (Rectangle(50, 50).t beside Rectangle(30, 70).t).fill(Color.cyan)
    g3.translate(30, 10).render(ctx)
    
    val p = CompoundPath(
        SimplePath((0, 0), LineSegment(1, 1)),
        SimplePath((0, 0), LineSegment(0, 1), LineSegment(1, 1), LineSegment(1, 0), CloseSegment)
        )
    val g4 = Shape(p, RectBounds(0, 100, 0, 100)).fill(Color.clear).stroke(Color.blue).width(5)
    g4.translate(200, 5).render(ctx)
  }

  /** Computes the square of an integer.
   *  This demonstrates unit testing.
   */
  def square(x: Int): Int = x*x
}
