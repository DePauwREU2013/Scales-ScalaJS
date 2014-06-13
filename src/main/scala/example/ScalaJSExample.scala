package example

import scala.scalajs.js
import org.scalajs.dom
import scala.language.implicitConversions
import scala.language.reflectiveCalls
import edu.depauw.scales.graphics._

object ScalaJSExample extends js.JSApp {
  implicit def decorateContext(ctx: dom.CanvasRenderingContext2D) = new {
    def strokeOval(x: Double, y: Double, w: Double, h: Double): Unit = {
      ctx.save()
      ctx.translate(x, y)
      ctx.scale(w/h, 1)
      ctx.beginPath()
      ctx.arc(h/2, h/2, h/2, 0, 2*math.Pi)
      ctx.restore()
      ctx.stroke()
    }
    
    def fillOval(x: Double, y: Double, w: Double, h: Double): Unit = {
      ctx.save()
      ctx.translate(x, y)
      ctx.scale(w/h, 1)
      ctx.beginPath()
      ctx.arc(h/2, h/2, h/2, 0, 2*math.Pi)
      ctx.restore()
      ctx.fill()
    }
  }
  
  def main(): Unit = {
    val playground = dom.document.getElementById("playground")
    
    val paragraph = dom.document.createElement("p")
    paragraph.innerHTML = "<strong>It works!</strong>"
    playground.appendChild(paragraph)
    
    val canvas = dom.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
    canvas.height = 200
    canvas.width = 600
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    ctx.fillStyle = "rgb(0,0,255)"
    ctx.fillRect(0, 0, 100, 100)
    playground.appendChild(canvas)
    
// Gradients currently break the Eclipse web browser view...
//    val gradient = ctx.createRadialGradient(200, 50, 10, 200, 50, 20)
//    gradient.addColorStop(0, "rgb(255, 0, 0)")
//    gradient.addColorStop(0.5, "rgb(0, 255, 0)")
//    gradient.addColorStop(1, "rgb(0, 0, 255")
    ctx.translate(200, 50)   //
    ctx.rotate(0.7)          // rotate 0.7 radians clockwise about (200, 50)
    ctx.translate(-200, -50) //
    ctx.fillStyle = "rgb(255, 128, 0)" // gradient
    ctx.fillOval(150, 20, 100, 60)
    ctx.translate(200, 50)
    ctx.rotate(-0.7)
    ctx.translate(-200, -50)
    
    ctx.beginPath()
    ctx.moveTo(200, 20)
    ctx.lineTo(200, 80)
    ctx.moveTo(150, 50)
    ctx.lineTo(250, 50)
    ctx.stroke()
    
    ctx.strokeRect(100, 100, 200, 100)
    
    ctx.lineWidth = 5
    ctx.strokeOval(100, 100, 200, 100)
    
    ctx.fillStyle = "rgba(0, 255, 0, 0.5)"
    ctx.fillRect(100, 100, 200, 100)
    
    import Base._
    
    val g: Graphic = Rectangle(80, 50) beside Ellipse(50, 80).rotate(20 deg).translate(0, 50)
    g.translate(400, 10).render(ctx)
    
    ctx.fillStyle = "rgb(255, 255, 0)"
    (Rectangle(80, 50) atop Ellipse(50, 80)).translate(10, 125).render(ctx)
    
    val p2 = dom.document.createElement("p")
    p2.innerHTML = s"${canvas.width} by ${canvas.height}"
    playground.appendChild(p2)
  }

  /** Computes the square of an integer.
   *  This demonstrates unit testing.
   */
  def square(x: Int): Int = x*x
}
