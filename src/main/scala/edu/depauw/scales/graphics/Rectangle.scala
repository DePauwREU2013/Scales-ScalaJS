package edu.depauw.scales.graphics

import Base._

case class Rectangle(width: Double, height: Double) extends Graphic {
  val bounds: Bounds = RectBounds(-width/2, width/2, -height/2, height/2)
  
  def render(ctx: GraphicsContext): Unit = {
    ctx.fillRect(-width/2, -height/2, width, height)
    ctx.strokeRect(-width/2, -height/2, width, height)
  }
}