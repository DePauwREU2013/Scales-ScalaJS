package edu.depauw.scales.graphics

import Base._

case class Rectangle(width: Double, height: Double) extends Graphic {
  val bounds: Bounds = RectBounds(0, width, 0, height)
  
  def render(ctx: GraphicsContext): Unit = {
    ctx.fillRect(0, 0, width, height)
    ctx.strokeRect(0, 0, width, height)
  }
}