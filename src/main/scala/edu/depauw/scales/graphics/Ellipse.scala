package edu.depauw.scales.graphics

import Base._

case class Ellipse(width: Double, height: Double) extends Graphic {
  val bounds: Bounds = RectBounds(-width/2, width/2, -height/2, height/2)

  def render(ctx: GraphicsContext): Unit = {
    ctx.fillOval(-width/2, -height/2, width, height)
    ctx.strokeOval(-width/2, -height/2, width, height)
  }
}