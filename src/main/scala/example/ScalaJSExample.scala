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
    paragraph.innerHTML = "<strong>It works! Location = " + window.toString + "</strong>"
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
    
    ctx.fillStyle = "rgba(0, 255, 0, 0.5)"
    ctx.lineWidth = 5
    
    val g = Rectangle(80, 50) beside Ellipse(50, 80).rotate(20 deg).translate(0, 50)
    g.translate(200, 100).render(ctx)
    
    ctx.fillStyle = "rgb(255, 255, 0)"
    val g2 = Rectangle(80, 50) atop Ellipse(50, 80)
    g2.translate(100, 150).render(ctx)
    
    val g3 = Rectangle(50, 50).t beside Rectangle(30, 70).t
    g3.translate(30, 10).render(ctx)
  }

  /** Computes the square of an integer.
   *  This demonstrates unit testing.
   */
  def square(x: Int): Int = x*x
}
