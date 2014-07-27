package edu.depauw.scales.reactive

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import Base._

object Canvas {
  val canvas = dom.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
  canvas.width = 750
  canvas.height = 500
  val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  val playground = dom.document.getElementById("playground")
  playground.appendChild(canvas)
}

object CanvasHandler {
  val ctx = Canvas.ctx
  val width = Canvas.canvas.width
  val height = Canvas.canvas.height

  val graphics: Var[List[Graphic]] = Var(Nil)

  def getIndex(): Int = {
    return graphics().size
  }

  def addGraphic(g: Graphic): Unit = { 
    graphics() = graphics() :+ g 
  }

  def updateGraphic(index: Int, g: Graphic): Unit = {
    graphics() = graphics().patch(index, List(g), 1)
  }

  Obs(graphics) {
    ctx.clearRect(0, 0, width, height)
    for(i <- 0 until graphics().length) {
      graphics()(i).render(ctx)
    }
  }
}